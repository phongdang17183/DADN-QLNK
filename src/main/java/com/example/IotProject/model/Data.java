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
@Table(name = "datas")
@Getter
@Setter
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long deviceId;
    private Timestamp time;
    private Long value;

    @ManyToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "id", insertable = false, updatable = false)
    Device device;
}
