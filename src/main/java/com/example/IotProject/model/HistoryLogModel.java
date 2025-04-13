package com.example.IotProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "history_logs")
@AllArgsConstructor
@NoArgsConstructor
public class HistoryLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

}
