package com.example.IotProject.repository;

import com.example.IotProject.model.DeviceDataModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface DeviceDataRepository extends JpaRepository<DeviceDataModel, Long> {
    List<DeviceDataModel> findByDevice_feedName(String device_id);

    @Query(value =
            "SELECT d.* FROM device_data d " +
                    "INNER JOIN devices dm ON d.device_id = dm.feed_name " +
                    "WHERE dm.feed_name = :deviceId " +
                    "AND d.time BETWEEN :startTime AND :endTime " +
                    "ORDER BY d.time ASC",
            nativeQuery = true)
    List<DeviceDataModel> findByDeviceIdAndTimeRangeNative(
            @Param("deviceId") String deviceId,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime
    );

    @Query(value =
            "TODO",
            nativeQuery = true)
    List<DeviceDataModel> findByZoneIdAndTimeRangeNative(
            @Param("deviceId") Long zoneId,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime
    );
}
