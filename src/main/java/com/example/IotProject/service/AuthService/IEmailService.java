package com.example.IotProject.service.AuthService;

import com.example.IotProject.dto.EmailDTO;
import com.example.IotProject.model.RuleModel;
import com.example.IotProject.response.RuleResponse.ConditionRuleResponse;

public interface IEmailService {
    public void sendEmail(EmailDTO email);
    public void sendOTPEmail(String fullName,String toEmail, String otp);
    public void sendMailArletTemperature(String data, ConditionRuleResponse res, RuleModel rule);
    public void sendMailAlertLight(String data, ConditionRuleResponse res, RuleModel rule);
    public void sendMailAlertHumidity(String data, ConditionRuleResponse res, RuleModel rule);
    public void sendMailAlertSoilMoisture(String data, ConditionRuleResponse res, RuleModel rule);


}
