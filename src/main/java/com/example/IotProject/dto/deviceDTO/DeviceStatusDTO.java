package com.example.IotProject.dto.deviceDTO;

import com.example.IotProject.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStatusDTO {
    private String userName;
    private String feedName;
    private DeviceStatus status;
}
