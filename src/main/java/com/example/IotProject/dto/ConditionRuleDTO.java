package com.example.IotProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionRuleDTO {
    private String name;
    private String relational_operator;
    private String value;
    private Long ruleId;
}
