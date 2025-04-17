package com.example.IotProject.service.RuleService;

import java.util.List;

import com.example.IotProject.dto.RuleDTO;
import com.example.IotProject.model.RuleModel;

public interface IRuleService {
    public void createRule(RuleDTO ruleDTO);
    public void updateRule(Long id, RuleDTO ruleDTO);
    public void deleteRule(Long id);
    public RuleModel getRuleById(Long id);
    public List<RuleModel> getAllRules();
    public List<RuleModel> getRulesbyDevice(String feedname);

}