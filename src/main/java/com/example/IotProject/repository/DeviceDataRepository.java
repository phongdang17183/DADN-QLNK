package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDataRepository extends JpaRepository<DeviceDataModel, Long> {
    List<DeviceDataModel> findByDevice_feedName(String device_id);
}
