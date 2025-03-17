package com.example.IotProject.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.enums.DeviceType;
import jakarta.persistence.*;
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

    private String feedName;
    private String deviceName;

    @Enumerated(EnumType.STRING)
    @Column(name = "subtype")
    private DeviceSubType subType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DeviceType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeviceStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private Zone zone;

    @OneToMany(mappedBy = "id")
    private List<DeviceLog> deviceLogs;

    @OneToMany(mappedBy = "id")
    private List<Rule> rules;

    @OneToMany(mappedBy = "id")
    private List<DeviceData> deviceData;
}
