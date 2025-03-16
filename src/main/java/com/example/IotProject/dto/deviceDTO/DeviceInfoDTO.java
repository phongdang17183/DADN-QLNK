package com.example.IotProject.dto.deviceDTO;

import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeviceInfoDTO {
    private String feedName;
    private String deviceName;
    private DeviceSubType subtype;
    private DeviceType type;
    private DeviceStatus status;
    private LocalDateTime createdAt;
    private Long zoneId;
}
