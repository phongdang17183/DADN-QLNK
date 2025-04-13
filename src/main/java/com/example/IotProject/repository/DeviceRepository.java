package com.example.IotProject.repository;

import com.example.IotProject.enums.DeviceType;
import com.example.IotProject.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceModel, String> {
    DeviceModel findByFeedName(String feedName);
    List<DeviceModel> findByType(DeviceType deviceType);
    List<DeviceModel> findByZoneId(Long zoneId);
}
