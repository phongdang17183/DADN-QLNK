package com.example.IotProject.component.eventListener;

import com.example.IotProject.component.event.MQTTMessageReceivedEvent;
import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.model.RuleModel;
import com.example.IotProject.model.DeviceModel;
import com.example.IotProject.response.RuleResponse.ConditionRuleResponse;
import com.example.IotProject.service.AuthService.IEmailService;
import com.example.IotProject.service.DeviceService.IDeviceService;
import com.example.IotProject.service.HistoryLogService.DeviceLogService;
import com.example.IotProject.service.RuleService.ConditionRuleService;
import com.example.IotProject.service.RuleService.RuleService;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class RuleEventListener {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ConditionRuleService conditionRuleService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private AdafruitClientServiceMQTT adafruitClientServiceMQTT;

    @Autowired
    private DeviceLogService deviceLogServicel;

    @Autowired
    private IEmailService emailService;

    @EventListener
    @Async
    public void handleMQTTEvent(MQTTMessageReceivedEvent event) {
        try {

            String feedName = event.getFeedName();
            Float data = event.getValue();

            DeviceModel device = deviceService.findByFeed(feedName);
            if (device == null) {
                return;
            }

            List<RuleModel> ruleModels = ruleService.getRulesbyDevice(device.getFeedName());

            if (ruleModels == null || ruleModels.isEmpty()) {
                System.out.println("No rule found for: " + device.getFeedName());
                return;
            }

            for (RuleModel rule : ruleModels) {
                // Lấy về tất cả ConditionRule nào thuộc rule này
                List<ConditionRuleResponse> conditions = conditionRuleService.getConditionByRule(rule.getId());
                if (conditions == null || conditions.isEmpty()) {
                    System.out.println("No condition rule found for: " + rule.getId());
                    return;
                }

                boolean isSatisfied = false;
                for (ConditionRuleResponse cond : conditions) {
                    System.out.println(">>> Checking condition: " + cond.getName() + " with value: " + data);
                    if (checkCondition(data, cond.getMinValue(), cond.getMaxValue(), cond.getStartDate(), cond.getEndDate())) {
                        isSatisfied = true;
                        break;
                    }
                }
                if (isSatisfied) {
                    System.out.println(">>> Rule matched (ID=" + rule.getId() + "), perform action: " + rule.getAction());
                    String action = rule.getDevice().getFeedName() + '/' + data + " is in range: "
                            + "[" + conditions.getFirst().getMinValue() + ","
                            + conditions.getFirst().getMaxValue() + "]";
                    deviceLogServicel.createDeviceLog(action, rule.getDevice().getFeedName(), Timestamp.valueOf(LocalDateTime.now()));
                    sendEmail(data.toString(), conditions.getFirst(),rule);
                    doAction(rule);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm check operator (>, <, >=, <=, = ...)
    private boolean checkCondition(Float actualValue ,String minValue, String maxValue, LocalDateTime startDate, LocalDateTime endDate) {
        if (actualValue == null) return false;
        Float fMinValue = Float.parseFloat(minValue);
        Float fMaxValue = Float.parseFloat(maxValue);
        LocalDateTime now = LocalDateTime.now();
        if (startDate != null && endDate != null) {
            if (now.isBefore(startDate) || now.isAfter(endDate)) {
                System.out.println("Nam ngoai thoi gian");
                return false; // Không nằm trong khoảng thời gian quy định
            }
        }
        return actualValue >= fMinValue && actualValue <= fMaxValue;

    }

    private void sendEmail(String data, ConditionRuleResponse conditionRuleResponse, RuleModel ruleModel){
        String[] sensorType = ruleModel.getDevice().getFeedName().split("-");
        System.out.println(sensorType[0]+"------------------------------------");
        if (Objects.equals(sensorType[0], "soil")){
            emailService.sendMailAlertSoilMoisture(data,conditionRuleResponse,ruleModel);
        } else if (Objects.equals(sensorType[0], "light")) {
            emailService.sendMailAlertLight(data,conditionRuleResponse,ruleModel);
        } else if (Objects.equals(sensorType[0], "humidity")) {
            emailService.sendMailAlertHumidity(data,conditionRuleResponse,ruleModel);
        } else if (Objects.equals(sensorType[0], "temp")) {
            emailService.sendMailArletTemperature(data,conditionRuleResponse,ruleModel);
        }
    }



    private void doAction(RuleModel rule) {

        System.out.println(">>> [ACTION] " + rule.getAction() + " is triggered...");
        // action dạng "pump-1/on"
        String action = rule.getAction();

        if (action != null && action.contains("/")) {
            String[] parts = action.split("/");
            if (parts.length == 2) {
                String feedName = parts[0];
                DeviceStatus status = deviceService.getStatusDevice(feedName);
                if ("on".equals(parts[1])){
                    // Nếu trạng thái hiện tại là ON thì không làm gì cả
                    if (status == DeviceStatus.ENABLE) {
                        System.out.println(">>> [ACTION] " + feedName + " is already ON, no action taken.");
                        return;
                    }
                    adafruitClientServiceMQTT.publishMessage(2.0f, feedName);
                    deviceService.updateStatusDevice(feedName, DeviceStatus.ENABLE);
                    deviceLogServicel.createDeviceLog(action, feedName, Timestamp.valueOf(LocalDateTime.now()));
                } else if ("off".equals(parts[1]) ){
                    // Nếu trạng thái hiện tại là OFF thì không làm gì cả
                    if (status == DeviceStatus.DISABLE) {
                        System.out.println(">>> [ACTION] " + feedName + " is already OFF, no action taken.");
                        return;
                    }
                    adafruitClientServiceMQTT.publishMessage(1.0f, feedName);
                    deviceService.updateStatusDevice(feedName, DeviceStatus.DISABLE);
                    deviceLogServicel.createDeviceLog(action, feedName, Timestamp.valueOf(LocalDateTime.now()));
                }
                // notificate
            } else {
                System.out.println("Action không đúng định dạng <feed>/<value>");
            }
        } else {
            System.out.println("Action không có dấu '/', không thể cắt chuỗi: " + action);
        }
    }

}