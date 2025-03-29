package com.example.IotProject.service;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.IotProject.model.ConditionRuleModel;
import com.example.IotProject.repository.ConditionRuleRepository;
import com.example.IotProject.response.ConditionRuleResponse;


@Service
public class ConditionRuleService {
    private final ConditionRuleRepository conditionRuleRepository;
    private final CacheManager cacheManager;

    public ConditionRuleService(ConditionRuleRepository conditionRuleRepository
    , CacheManager cacheManager) {
        this.conditionRuleRepository = conditionRuleRepository;
        this.cacheManager = cacheManager;
    }


    @Cacheable(value = "rulesCache", key = "'allRules'")
    public List<ConditionRuleResponse> getRulesFromDatabase() {
        List<ConditionRuleModel> conditionRuleModels = conditionRuleRepository.findAll();
        List<ConditionRuleResponse> conditionRuleResponses = conditionRuleModels.stream()
                .map(conditionRuleModel -> new ConditionRuleResponse(conditionRuleModel.getId(), conditionRuleModel.getName(), conditionRuleModel.getRelational_operator(), conditionRuleModel.getValue()))
                .toList();
        return conditionRuleResponses;
    }

    @CachePut(value = "rulesCache", key = "#result.id")
    public ConditionRuleModel saveRule(ConditionRuleModel conditionRuleModel) {
        return conditionRuleRepository.save(conditionRuleModel);
    }
    @CacheEvict(value = "rulesCache", key = "#conditionRuleModel.id")
    public void deleteRule(ConditionRuleModel conditionRuleModel) {
        conditionRuleRepository.delete(conditionRuleModel);
    }

    public List<ConditionRuleResponse> getRules() {
        Cache cache = cacheManager.getCache("rulesCache");
        if (cache != null) {
            // Lấy cache theo key đã chỉ định trong @Cacheable
            List<ConditionRuleResponse> rules = cache.get("allRules", List.class); 
            if (rules != null) {
                return rules;
            }
        }
        return null;
    }
    

    // @CacheEvict(value = "rulesCache", allEntries = true)
    // public void updateCacheOnRuleChange() {

    // }
}
