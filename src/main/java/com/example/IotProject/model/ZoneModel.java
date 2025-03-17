package com.example.IotProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "zones")
@Getter
@Setter
public class ZoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private List<DeviceModel> devices;
}
