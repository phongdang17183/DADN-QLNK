package com.example.IotProject.component.eventListener;

import com.example.IotProject.component.event.MQTTMessageReceivedEvent;
import com.example.IotProject.enums.RuleOperator;
import com.example.IotProject.model.RuleModel;
import com.example.IotProject.model.ConditionRuleModel;
import com.example.IotProject.model.DeviceModel;
import com.example.IotProject.service.ConditionRuleService;
import com.example.IotProject.service.DeviceService;
import com.example.IotProject.service.RuleService;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RuleEventListener {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ConditionRuleService conditionRuleService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AdafruitClientServiceMQTT adafruitClientServiceMQTT;

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
                List<ConditionRuleModel> conditions = conditionRuleService.getConditionByRule(rule.getId());
                if (conditions == null || conditions.isEmpty()) {
                    System.out.println("No condition rule found for: " + rule.getId());
                    return;
                }

                boolean isSatisfied = false;
                for (ConditionRuleModel cond : conditions) {
                    System.out.println(">>> Checking condition: " + cond.getName() + " with value: " + data);
                    if (checkCondition(data, cond.getMinValue(), cond.getMaxValue(), cond.getStartDate(), cond.getEndDate())) {
                        isSatisfied = true;
                        break;
                    }
                }
                if (isSatisfied) {
                    System.out.println(">>> Rule matched (ID=" + rule.getId() + "), perform action: " + rule.getAction());
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
                return false; // Không nằm trong khoảng thời gian quy định
            }
        }
        return actualValue >= fMinValue && actualValue <= fMaxValue;

        // return switch (operator) {
        //     case GREATER -> actualValue > threshold;
        //     case GREATEREQUAL -> actualValue >= threshold;
        //     case LESS -> actualValue < threshold;
        //     case LESSEQUAL -> actualValue <= threshold;
        //     case EQUAL -> actualValue.equals(threshold);
        //     default -> false;
        // };
    }


    private void doAction(RuleModel rule) {

        System.out.println(">>> [ACTION] " + rule.getAction() + " is triggered...");
        // action dạng "pump-1/on"
        String action = rule.getAction();

        if (action != null && action.contains("/")) {
            String[] parts = action.split("/");
            if (parts.length == 2) {
                String feedName = parts[0];
                if ("on".equals(parts[1])){
                    adafruitClientServiceMQTT.publishMessage(2.0f, feedName);
                } else if ("off".equals(parts[1]) ){
                    adafruitClientServiceMQTT.publishMessage(1.0f, feedName);
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
