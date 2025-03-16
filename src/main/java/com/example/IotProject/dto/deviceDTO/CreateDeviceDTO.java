package com.example.IotProject.dto.deviceDTO;

import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateDeviceDTO {
    private String deviceName;
    private DeviceType type;
    private DeviceSubType subType;
    private Long zoneId;
}
