package com.example.IotProject.service;

import com.example.IotProject.dto.deviceDTO.CreateDeviceDTO;
import com.example.IotProject.dto.deviceDTO.DeviceInfoDTO;
import com.example.IotProject.dto.deviceDTO.DeviceStatusDTO;
import com.example.IotProject.enums.DeviceStatus;
import com.example.IotProject.enums.DeviceSubType;
import com.example.IotProject.exception.CreateFeedFailedException;
import com.example.IotProject.exception.ZoneNotFoundException;
import com.example.IotProject.model.*;
import com.example.IotProject.repository.*;
import com.example.IotProject.service.adafruitService.AdaFruitClientServiceHTTP;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Cacheable;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final HistoryLogService historyLogService;
    private final AdafruitClientServiceMQTT adafruitClientServiceMQTT;

    @Autowired
    public DeviceService(
            DeviceRepository deviceRepository,
            ZoneRepository zoneRepository,
            ManagementRepository managementRepository,
            UserRepository userRepository,
            AdaFruitClientServiceHTTP adaFruitServiceHTTP,
            AdafruitClientServiceMQTT mqttServiceMQTT,
            @Value("${mqtt.username}") String userName,
            UserService userService,
            HistoryLogService historyLogService,
            AdafruitClientServiceMQTT adafruitClientServiceMQTT
    ) {
        this.deviceRepository = deviceRepository;
        this.zoneRepository = zoneRepository;
        this.managementRepository = managementRepository;
        this.userRepository = userRepository;
        this.adaFruitServiceHTTP = adaFruitServiceHTTP;
        this.mqttServiceMQTT = mqttServiceMQTT;
        this.userName = userName;
        this.userService = userService;
        this.historyLogService = historyLogService;
        this.adafruitClientServiceMQTT = adafruitClientServiceMQTT;
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

    public List<DeviceModel> findByZoneId(Long zoneId){
        return deviceRepository.findByZoneId(zoneId);
    }

//    @Cacheable(value = "status_device" ,key="#feedName")
    public DeviceStatus getStatusDevice(String feedName) {
        DeviceModel device = findByFeed(feedName);
        return device.getStatus();
    }
    @CacheEvict(value = "status_device", key = "#feedName")
    public String updateStatusDevice(String feedName, DeviceStatus status) {        // for auto device
        DeviceModel device = findByFeed(feedName);
        if (device != null) {
            device.setStatus(status);
            deviceRepository.save(device);
            return "Device status updated successfully";
        }
        return "Device not found";
    }

    public String device_status(DeviceStatusDTO deviceStatusDTO){                   // for user control device
        DeviceModel device = deviceRepository.findByFeedName(deviceStatusDTO.getFeedName());
        if (device == null) {
            throw new RuntimeException("Device not found");
        }
        device.setStatus(deviceStatusDTO.getStatus());
        deviceRepository.save(device);

        if(deviceStatusDTO.getStatus() == DeviceStatus.ENABLE){
            // subscribe feed
            adafruitClientServiceMQTT.listenToFeed(deviceStatusDTO.getFeedName());
        } else if (deviceStatusDTO.getStatus() == DeviceStatus.DISABLE) {
            // unsubscribe feed
            adafruitClientServiceMQTT.unlistenToFeed(deviceStatusDTO.getFeedName());
        }

        String action = deviceStatusDTO.getStatus()+ "/" + deviceStatusDTO.getFeedName();
        historyLogService.logWSHistory(action, deviceStatusDTO.getUserName() , Timestamp.valueOf(java.time.LocalDateTime.now()) );

        return "Status updated successfully";
    }
}
