package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<DeviceDataModel, Integer> {
}
