package com.example.IotProject.model;

import com.example.IotProject.enums.RuleOperator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    RuleModel rule;
}
