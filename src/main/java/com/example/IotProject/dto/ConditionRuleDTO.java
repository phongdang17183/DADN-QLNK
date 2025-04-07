package com.example.IotProject.dto;

import com.example.IotProject.enums.RuleOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionRuleDTO {
    private String name;
    private RuleOperator relational_operator;
    private String value;
    private Long ruleId;
}
