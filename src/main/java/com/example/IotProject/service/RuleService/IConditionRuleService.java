package com.example.IotProject.service.RuleService;

import java.util.List;

import com.example.IotProject.dto.ConditionRuleDTO;
import com.example.IotProject.response.RuleResponse.ConditionRuleResponse;

public interface IConditionRuleService {
    public List<ConditionRuleResponse> getAll();
    public ConditionRuleResponse getRuleById(Long id);
    public void addRule(ConditionRuleDTO conditionRuleDTO);
    public void updateRule(Long id, ConditionRuleDTO conditionRuleDTO);
    public void deleteRule(Long id);
    public List<ConditionRuleResponse> getConditionByRule(Long rule_id);
}
