package com.example.IotProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.model.ConditionRuleModel;
import com.example.IotProject.service.ConditionRuleService;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/rules")
public class RuleController {
    private final ConditionRuleService conditionRuleService;
    private final CacheManager cacheManager;
    public RuleController(ConditionRuleService conditionRuleService, CacheManager cacheManager) {
        this.conditionRuleService = conditionRuleService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/getRules")
    public ResponseEntity<?> getRules() {
        List<ConditionRuleModel> rules = cacheManager.getCache("rulesCache").get("rules", List.class);
        
        
        
        return ResponseEntity.ok(rules);
    }

}
