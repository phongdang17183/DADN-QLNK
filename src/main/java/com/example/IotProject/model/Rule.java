package com.example.IotProject.model;

import java.security.Timestamp;

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
@Table(name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private Timestamp createdAt;
    private Long createBy;
    private Long deviceId;

    @ManyToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "id", insertable = false, updatable = false)
    Device device;

    @ManyToOne
    @JoinColumn(name = "createBy", referencedColumnName = "id", insertable = false, updatable = false)
    User user;
}
