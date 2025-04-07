package com.example.IotProject.service;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.IotProject.dto.ConditionRuleDTO;
import com.example.IotProject.model.ConditionRuleModel;
import com.example.IotProject.model.RuleModel;
import com.example.IotProject.repository.ConditionRuleRepository;
import com.example.IotProject.response.ConditionRuleResponse;


@Service
public class ConditionRuleService {
    private final ConditionRuleRepository conditionRuleRepository;
    private final CacheManager cacheManager;

    private final RuleService ruleService;

    public ConditionRuleService(ConditionRuleRepository conditionRuleRepository, CacheManager cacheManager, RuleService ruleService) {
        this.conditionRuleRepository = conditionRuleRepository;
        this.cacheManager = cacheManager;
        this.ruleService = ruleService;
    }

    @Cacheable(value = "rulesCache", key = "'all'")
    public List<ConditionRuleResponse> getAll() {
        List<ConditionRuleModel> conditionRuleModels = conditionRuleRepository.findAll();
        List<ConditionRuleResponse> conditionRuleResponses = conditionRuleModels.stream()
                .map(conditionRuleModel -> new ConditionRuleResponse(
                    conditionRuleModel.getId(),
                    conditionRuleModel.getName(),
                    conditionRuleModel.getRelational_operator(),
                    conditionRuleModel.getValue()))
                .toList();
        return conditionRuleResponses;
    }

    public ConditionRuleResponse getRuleById(Long id) {
        ConditionRuleModel conditionRuleModel = conditionRuleRepository.findById(id).orElseThrow(() -> new RuntimeException("Condition rule not found"));
        return new ConditionRuleResponse(
            conditionRuleModel.getId(),
            conditionRuleModel.getName(),
            conditionRuleModel.getRelational_operator(),
            conditionRuleModel.getValue());
    }

    @CacheEvict(value = "rulesCache", key = "'all'")
    public void deleteRule(Long id ) {
        conditionRuleRepository.deleteById(id);
    }
    @CacheEvict(value = "rulesCache", key = "'all'")
    public void addRule(ConditionRuleDTO conditionRuleDTO) {
        if (conditionRuleRepository.existsByNameAndRelationalOperatorAndValue(
            conditionRuleDTO.getName(),
            conditionRuleDTO.getRelational_operator(),
            conditionRuleDTO.getValue())) {
            throw new RuntimeException("Condition rule already exists with the same name, relational operator, and value.");
        }
        ConditionRuleModel conditionRuleModel = new ConditionRuleModel();
        conditionRuleModel.setName(conditionRuleDTO.getName());
        conditionRuleModel.setRelational_operator(conditionRuleDTO.getRelational_operator());
        conditionRuleModel.setValue(conditionRuleDTO.getValue());
        RuleModel ruleModel = ruleService.getRuleById(conditionRuleDTO.getRuleId());
        if (ruleModel == null) {
            throw new RuntimeException("Rule not found with id: " + conditionRuleDTO.getRuleId());
        }
        conditionRuleModel.setRule(ruleModel);
        conditionRuleModel = conditionRuleRepository.save(conditionRuleModel);

        if (conditionRuleModel.getId() == null) {
            throw new RuntimeException("Failed to save ConditionRuleModel, id is null");
        }
        ConditionRuleResponse conditionRuleResponse = new ConditionRuleResponse(
            conditionRuleModel.getId(),
            conditionRuleModel.getName(),
            conditionRuleModel.getRelational_operator(),
            conditionRuleModel.getValue()
        );
        Cache cache = cacheManager.getCache("rulesCache");
        if (cache != null) {
            cache.put(conditionRuleModel.getId(), conditionRuleResponse);
        }
        return;
    }


    @CacheEvict(value = "rulesCache", key = "'all'")
    public void updateRule(Long id, ConditionRuleDTO conditionRuleDTO) {
        ConditionRuleModel conditionRuleModel = conditionRuleRepository.findById(id).orElseThrow(() -> new RuntimeException("Condition rule not found"));
        conditionRuleModel.setName(conditionRuleDTO.getName());
        conditionRuleModel.setRelational_operator(conditionRuleDTO.getRelational_operator());
        conditionRuleModel.setValue(conditionRuleDTO.getValue());
        System.out.println(conditionRuleModel);
        conditionRuleRepository.save(conditionRuleModel);
        ConditionRuleResponse conditionRuleResponse = new ConditionRuleResponse(
            conditionRuleModel.getId(),
            conditionRuleModel.getName(),
            conditionRuleModel.getRelational_operator(),
            conditionRuleModel.getValue());
        Cache cache = cacheManager.getCache("rulesCache");
        if (cache != null) {
            cache.put(conditionRuleModel.getId(), conditionRuleResponse);
        }
    }

    public List<ConditionRuleModel> getConditionByRule(Long rule_id){
        List<ConditionRuleModel> conditionRuleModels = conditionRuleRepository.findByRuleId(rule_id);
        if (conditionRuleModels == null || conditionRuleModels.isEmpty()) {
            throw new RuntimeException("No condition found for rule_id: " + rule_id);
        }
        return conditionRuleModels;

    }
}
