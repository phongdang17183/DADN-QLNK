package com.example.IotProject.response;

import com.example.IotProject.enums.RuleOperator;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ConditionRuleResponse {
    private Long id;
    private String name;
    private RuleOperator relational_operator;
    private String value;
}
