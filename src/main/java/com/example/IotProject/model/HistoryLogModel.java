package com.example.IotProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "history_logs")
public class HistoryLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "action", length = 20)
    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<DeviceLogModel> deviceLogs;

}
