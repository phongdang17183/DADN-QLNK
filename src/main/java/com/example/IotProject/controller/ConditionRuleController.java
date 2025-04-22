package com.example.IotProject.controller;

import com.example.IotProject.service.RuleService.ConditionRuleService;
import com.example.IotProject.service.RuleService.IConditionRuleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.dto.ConditionRuleDTO;

@RestController
@RequestMapping("/api/v1/condition-rules")
public class ConditionRuleController {
    private final IConditionRuleService conditionRuleService;
    public ConditionRuleController(ConditionRuleService conditionRuleService) {
        this.conditionRuleService = conditionRuleService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createConditionRule(@Valid @RequestBody ConditionRuleDTO conditionRuleDTO) {
        conditionRuleService.addRule(conditionRuleDTO);
        return ResponseEntity.ok("Condition rule created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateConditionRule(@RequestParam Long id, @RequestBody ConditionRuleDTO conditionRuleDTO) {
        conditionRuleService.updateRule(id, conditionRuleDTO);
        return ResponseEntity.ok("Condition rule updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteConditionRule(@RequestParam Long id) {
        conditionRuleService.deleteRule(id);
        return ResponseEntity.ok("Condition rule deleted successfully");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllConditionRules() {
        return ResponseEntity.ok(conditionRuleService.getAll());
    }

    @GetMapping("/getByRuleId")
    public ResponseEntity<?> getConditionRuleByRuleId(@RequestParam Long ruleId) {
        return ResponseEntity.ok(conditionRuleService.getConditionByRule(ruleId));
    }


    @GetMapping("/getById")
    public ResponseEntity<?> getConditionRuleById(@RequestParam Long id) {
        return ResponseEntity.ok(conditionRuleService.getRuleById(id));
    }
}
