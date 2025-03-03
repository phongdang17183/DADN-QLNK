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
@Table(name = "device_logs")
@Setter
@Getter
public class DeviceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long historyLogId;
    private Long deviceId;
    private String action;

    @ManyToOne
    @JoinColumn(name = "historyLogId", referencedColumnName = "id", insertable = false, updatable = false)
    HistoryLog historyLog;

    @ManyToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "id", insertable = false, updatable = false)
    Device device;
}
