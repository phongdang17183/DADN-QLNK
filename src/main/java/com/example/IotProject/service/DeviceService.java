package com.example.IotProject.service;

import com.example.IotProject.dto.deviceDto.CreateDeviceDTO;
import com.example.IotProject.dto.deviceDto.DeviceInfoDTO;
import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.exception.ZoneNotFoundException;
import com.example.IotProject.model.Device;
import com.example.IotProject.model.Zone;
import com.example.IotProject.repository.DeviceRepository;
import com.example.IotProject.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final ZoneRepository zoneRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, ZoneRepository zoneRepository) {
        this.deviceRepository = deviceRepository;
        this.zoneRepository = zoneRepository;
    }

    public String createZoneName(DeviceSubType subType, Long zoneId) {
        switch (subType) {
            case PUMP -> {
                return zoneId + "_pump";
            }
            case SERVO -> {
                return zoneId + "_servo";
            }
            case TEMPERATURE -> {
                return "temperature_" + zoneId;
            }
            case HUMIDITY -> {
                return "humidity_" + zoneId;
            }
            case SOIL_MOISTURE -> {
                return "soil_" + zoneId;
            }
            case LIGHT -> {
                return "light_" + zoneId;
            }
        }
        return null;
    }

    // TODO: This function add a feed of a device from AdaFruit
    public DeviceInfoDTO addDeviceFeed(CreateDeviceDTO createDeviceDTO)
    {
        Device device = new Device();
        device.setDeviceName(createDeviceDTO.getDeviceName());
        device.setType(createDeviceDTO.getType());
        device.setSubType(createDeviceDTO.getSubType());

        Zone zone = zoneRepository.findById((createDeviceDTO.getZoneId())).
                orElseThrow(() -> new ZoneNotFoundException("Zone not found with id: " + createDeviceDTO.getZoneId()));

        device.setStatus(DeviceStatus.ENABLE);
        device.setFeedName(createZoneName(createDeviceDTO.getSubType(), createDeviceDTO.getZoneId()));
        device.setCreatedAt(java.time.LocalDateTime.now());
        device.setZone(zone);

        deviceRepository.save(device);

        DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO();
        deviceInfoDTO.setFeedName(device.getFeedName());
        deviceInfoDTO.setDeviceName(device.getDeviceName());
        deviceInfoDTO.setSubtype(device.getSubType());
        deviceInfoDTO.setType(device.getType());
        deviceInfoDTO.setStatus(device.getStatus());
        deviceInfoDTO.setCreatedAt(device.getCreatedAt());
        deviceInfoDTO.setZoneId(device.getZone().getId());

        return deviceInfoDTO;
    }
}
