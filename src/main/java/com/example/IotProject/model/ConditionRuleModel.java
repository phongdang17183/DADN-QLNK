package com.example.IotProject.model;

import java.time.LocalDateTime;

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
    private String minValue;
    private String maxValue;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    RuleModel rule;
}
