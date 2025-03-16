package com.example.IotProject.service;

import com.example.IotProject.dto.deviceDTO.CreateDeviceDTO;
import com.example.IotProject.dto.deviceDTO.DeviceInfoDTO;
import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.exception.CreateFeedFailedException;
import com.example.IotProject.exception.ZoneNotFoundException;
import com.example.IotProject.model.Device;
import com.example.IotProject.model.Zone;
import com.example.IotProject.repository.DeviceRepository;
import com.example.IotProject.repository.ZoneRepository;
import com.example.IotProject.service.adafruitService.AdaFruitClientServiceHTTP;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final ZoneRepository zoneRepository;
    private final AdaFruitClientServiceHTTP adaFruitServiceHTTP;
    private final String userName;

    @Autowired
    public DeviceService(
            DeviceRepository deviceRepository,
            ZoneRepository zoneRepository,
            AdaFruitClientServiceHTTP adaFruitServiceHTTP,
            @Value("${mqtt.username}") String userName
    ) {
        this.deviceRepository = deviceRepository;
        this.zoneRepository = zoneRepository;
        this.adaFruitServiceHTTP = adaFruitServiceHTTP;
        this.userName = userName;
    }

    private Map<String, Object> parseJson(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response: " + e.getMessage(), e);
        }
    }

    private String createFeedName(DeviceSubType subType, Long zoneId) {
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
        // TODO: Check if the zone for the device exists
        Zone zone = zoneRepository.findById((createDeviceDTO.getZoneId())).
                orElseThrow(() -> new ZoneNotFoundException("Zone not found with id: " + createDeviceDTO.getZoneId()));

        // TODO: Create the feed of the device in AdaFruit
        String response = adaFruitServiceHTTP.createFeed(userName, createFeedName(createDeviceDTO.getSubType(), createDeviceDTO.getZoneId()));
        Map<String, Object> responseJSON = parseJson(response);
        if (responseJSON.containsKey("error")) {
            throw new CreateFeedFailedException("Failed to create feed: " + responseJSON.get("error"));
        }

        // TODO: Create and save the device to the database
        Device device = new Device();
        device.setDeviceName(createDeviceDTO.getDeviceName());
        device.setType(createDeviceDTO.getType());
        device.setSubType(createDeviceDTO.getSubType());
        device.setStatus(DeviceStatus.ENABLE);
        device.setFeedName(createFeedName(createDeviceDTO.getSubType(), createDeviceDTO.getZoneId()));
        device.setCreatedAt(java.time.LocalDateTime.now());
        device.setZone(zone);

        deviceRepository.save(device);

        // TODO: Return the device info DTO
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
