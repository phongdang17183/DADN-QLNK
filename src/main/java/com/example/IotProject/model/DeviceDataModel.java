package com.example.IotProject.model;

import java.security.Timestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "device_data")
@Getter
@Setter
public class DeviceDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp time;
    private Long value;

    @ManyToOne
    @JoinColumn(name = "device_id") // Removed referencedColumnName = "id"
    DeviceModel device;
}