package com.example.IotProject.model;

import com.example.IotProject.enums.RuleOperator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "condition_rules")
public class ConditionRuleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RuleOperator relational_operator;
    private String value;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    RuleModel rule;
}
