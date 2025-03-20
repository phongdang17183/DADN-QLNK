package com.example.IotProject.service;

import com.example.IotProject.dto.deviceDTO.CreateDeviceDTO;
import com.example.IotProject.dto.deviceDTO.DeviceInfoDTO;
import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.exception.CreateFeedFailedException;
import com.example.IotProject.exception.ZoneNotFoundException;
import com.example.IotProject.model.DeviceModel;
import com.example.IotProject.model.ManagementModel;
import com.example.IotProject.model.UserModel;
import com.example.IotProject.model.ZoneModel;
import com.example.IotProject.repository.DeviceRepository;
import com.example.IotProject.repository.ManagementRepository;
import com.example.IotProject.repository.UserRepository;
import com.example.IotProject.repository.ZoneRepository;
import com.example.IotProject.service.adafruitService.AdaFruitClientServiceHTTP;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final ZoneRepository zoneRepository;
    private final UserRepository userRepository;
    private final AdaFruitClientServiceHTTP adaFruitServiceHTTP;
    private final AdafruitClientServiceMQTT mqttServiceMQTT;
    private final String userName;
    private final UserService userService;
    private final ManagementRepository managementRepository;

    @Autowired
    public DeviceService(
            DeviceRepository deviceRepository,
            ZoneRepository zoneRepository,
            ManagementRepository managementRepository,
            UserRepository userRepository,
            AdaFruitClientServiceHTTP adaFruitServiceHTTP,
            AdafruitClientServiceMQTT mqttServiceMQTT,
            @Value("${mqtt.username}") String userName,
            UserService userService
    ) {
        this.deviceRepository = deviceRepository;
        this.zoneRepository = zoneRepository;
        this.managementRepository = managementRepository;
        this.userRepository = userRepository;
        this.adaFruitServiceHTTP = adaFruitServiceHTTP;
        this.mqttServiceMQTT = mqttServiceMQTT;
        this.userName = userName;
        this.userService = userService;
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
                return "pump-" + zoneId;
            }
            case SERVO -> {
                return "servo-" + zoneId;
            }
            case TEMPERATURE -> {
                return "temp-" + zoneId;
            }
            case HUMIDITY -> {
                return "airm-" + zoneId;
            }
            case SOIL_MOISTURE -> {
                return "soil-" + zoneId;
            }
            case LIGHT -> {
                return "light-" + zoneId;
            }
        }
        return null;
    }

    // TODO: This function add a feed of a device from AdaFruit
    public DeviceInfoDTO addDeviceFeed(CreateDeviceDTO createDeviceDTO)
    {
        // TODO: Check if the zone for the device exists
        ZoneModel zone = zoneRepository.findById((createDeviceDTO.getZoneId())).
                orElseThrow(() -> new ZoneNotFoundException("Zone not found with id: " + createDeviceDTO.getZoneId()));
        // TODO: Create the feed of the device in AdaFruit
        String response = adaFruitServiceHTTP.createFeed(userName, createFeedName(createDeviceDTO.getSubType(), createDeviceDTO.getZoneId()));
        Map<String, Object> responseJSON = parseJson(response);
        if (responseJSON.containsKey("error")) {
            throw new CreateFeedFailedException("Failed to create feed: " + responseJSON.get("error"));
            /* There are 2 possible errors here:
            1. The feed already exists
            2. The feed name is invalid
             */
        }
        // TODO: Create and save the device to the database
        DeviceModel device = new DeviceModel();
        device.setDeviceName(createDeviceDTO.getDeviceName());
        device.setType(createDeviceDTO.getType());
        device.setSubType(createDeviceDTO.getSubType());
        device.setStatus(DeviceStatus.ENABLE);
        device.setFeedName(createFeedName(createDeviceDTO.getSubType(), createDeviceDTO.getZoneId()));
        device.setCreatedAt(java.time.LocalDateTime.now());
        device.setZone(zone);

        // TODO: Add the device to management of the current user
        ManagementModel management = new ManagementModel();
        management.setDevice(device);
        UserModel user = userService.getCurrentUser();
        management.setUser(user);

        // Save the management, device and user to the database
        deviceRepository.save(device);
        userRepository.save(user);
        managementRepository.save(management);






        // TODO: Start listen to the feed of the device
        mqttServiceMQTT.listenToFeed(device.getFeedName());

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

    public DeviceModel findByFeed(String feedName){
        return deviceRepository.findByFeedName(feedName);
    }
}
