package com.example.IotProject.dto.deviceDto;

import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.enums.DeviceType;
import com.example.IotProject.model.Zone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateDeviceDTO {
    private String deviceName;
    private DeviceType type;
    private DeviceSubType subType;
    private Long zoneId;
}
