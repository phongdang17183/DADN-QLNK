package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceLogModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceLogRepository extends JpaRepository<DeviceLogModel, Long> {
    @Query("SELECT r FROM DeviceLogModel r WHERE r.device.id = :deviceId")
    List<DeviceLogModel> findByDeviceId(@Param("deviceId") String deviceId);
}
