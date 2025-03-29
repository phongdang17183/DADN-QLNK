package com.example.IotProject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionRuleResponse {
    Long id;
    String name;
    String relational_operator;
    String value;
}
