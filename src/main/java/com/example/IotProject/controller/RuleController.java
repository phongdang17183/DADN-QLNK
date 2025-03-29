package com.example.IotProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.IotProject.response.ConditionRuleResponse;
import com.example.IotProject.service.ConditionRuleService;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/rules")
public class RuleController {
    private final ConditionRuleService conditionRuleService;
    public RuleController(ConditionRuleService conditionRuleService) {
        this.conditionRuleService = conditionRuleService;
    }

    @GetMapping("/getRules")
    public ResponseEntity<?> getRules() {
        List<ConditionRuleResponse> rules = conditionRuleService.getRulesFromDatabase();
        return ResponseEntity.ok(rules);
    }

    @GetMapping("/getRulesCache")
    public ResponseEntity<?> getRulesCache() {
        List<ConditionRuleResponse> rules = conditionRuleService.getRules();
        return ResponseEntity.ok(rules);
    }


}
