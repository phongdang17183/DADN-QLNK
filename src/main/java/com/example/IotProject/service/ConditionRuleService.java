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


@Service
public class ConditionRuleService {
    private final ConditionRuleRepository conditionRuleRepository;
    private final CacheManager cacheManager;

    public ConditionRuleService(ConditionRuleRepository conditionRuleRepository
    , CacheManager cacheManager) {
        this.conditionRuleRepository = conditionRuleRepository;
        this.cacheManager = cacheManager;
    }


    @Cacheable(value = "rulesCache", key = "#conditionRuleId")
    public List<ConditionRuleModel> getRulesFromDatabase() {
        return conditionRuleRepository.findAll();
    }

    @CachePut(value = "rulesCache", key = "#result.id")
    public ConditionRuleModel saveRule(ConditionRuleModel conditionRuleModel) {
        return conditionRuleRepository.save(conditionRuleModel);
    }
    @CacheEvict(value = "rulesCache", key = "#conditionRuleModel.id")
    public void deleteRule(ConditionRuleModel conditionRuleModel) {
        conditionRuleRepository.delete(conditionRuleModel);
    }
    

    // @CacheEvict(value = "rulesCache", allEntries = true)
    // public void updateCacheOnRuleChange() {

    // }
}
