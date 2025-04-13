package com.example.IotProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "device_logs")
@Setter
@Getter
public class DeviceLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "device_id") // Removed referencedColumnName = "id"
    DeviceModel device;
}