package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<DeviceData, Integer> {
}
