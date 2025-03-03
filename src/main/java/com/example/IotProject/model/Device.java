package com.example.IotProject.model;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.Column;
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
@Table(name = "devices")
@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String status;
    private LocalDateTime createdAt;

    @Column(name = "zone_id")
    private Long zoneId;

    @ManyToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Zone zone;
}
