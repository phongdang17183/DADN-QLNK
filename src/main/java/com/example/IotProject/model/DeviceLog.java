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

    private String action;

    @ManyToOne
    @JoinColumn(name = "history_log_id", referencedColumnName = "id")
    HistoryLog historyLog;

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    Device device;
}
