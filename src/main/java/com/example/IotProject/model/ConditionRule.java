package com.example.IotProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "condition_rules")
public class ConditionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String relation;
    private String value;
    private Long ruleId;

    @ManyToOne
    @JoinColumn(name = "ruleId", referencedColumnName = "id", insertable = false, updatable = false)
    Rule rule;
}
