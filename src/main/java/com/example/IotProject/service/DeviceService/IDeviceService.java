package com.example.IotProject.service.DeviceService;

import java.util.List;
import java.util.Map;

import com.example.IotProject.dto.deviceDTO.CreateDeviceDTO;
import com.example.IotProject.dto.deviceDTO.DeviceInfoDTO;
import com.example.IotProject.dto.deviceDTO.DeviceStatusDTO;
import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.model.DeviceModel;

public interface IDeviceService {
    public DeviceInfoDTO addDeviceFeed(CreateDeviceDTO createDeviceDTO);
    public DeviceModel findByFeed(String feedName);
    public List<DeviceModel> findByZoneId(Long zoneId);
    public DeviceStatus getStatusDevice(String feedName);
    public String updateStatusDevice(String feedName, DeviceStatus status);
    public String device_status(DeviceStatusDTO deviceStatusDTO);

}
