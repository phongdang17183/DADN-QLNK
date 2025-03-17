package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceModel, Integer> {

}
