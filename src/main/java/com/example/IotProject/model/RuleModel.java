package com.example.IotProject.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rules")
public class RuleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "device_id") // Removed referencedColumnName = "id"
    private DeviceModel device;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserModel user;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConditionRuleModel> conditions;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}