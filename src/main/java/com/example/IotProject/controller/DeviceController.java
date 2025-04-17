package com.example.IotProject.controller;

import com.example.IotProject.dto.deviceDTO.CreateDeviceDTO;
import com.example.IotProject.dto.deviceDTO.DeviceInfoDTO;
import com.example.IotProject.dto.deviceDTO.DeviceStatusDTO;
import com.example.IotProject.model.DeviceModel;
import com.example.IotProject.response.StringResponse;
import com.example.IotProject.service.DeviceService.IDeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/devices")
public class DeviceController {

    private final IDeviceService deviceService;

    @Autowired
    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PreAuthorize("hasRole('ROLE_Technician')")
    @PostMapping("/add_device")
    public ResponseEntity<DeviceInfoDTO> addDeviceFeed(@RequestBody CreateDeviceDTO createDeviceDTO) {
        return ResponseEntity.ok(deviceService.addDeviceFeed(createDeviceDTO));
    }
    @GetMapping("/getStatus")
    public ResponseEntity<?> getDeviceStatus(@RequestParam String feedName) {
        return ResponseEntity.ok(deviceService.getStatusDevice(feedName));
    }

    @PreAuthorize("hasRole('ROLE_Technician')")
    @PatchMapping("/updatestatus")
    public ResponseEntity<StringResponse> updateDeviceStatus(@RequestBody DeviceStatusDTO deviceStatusDTO){
        return ResponseEntity.ok(new StringResponse(deviceService.device_status(deviceStatusDTO)));
    }

    @PreAuthorize("hasRole('ROLE_Technician')")
    @GetMapping("/zone/{id}")
    public ResponseEntity<List<DeviceModel>> findAllByZoneId(@PathVariable Long id){
        return ResponseEntity.ok(deviceService.findByZoneId(id));
    }
}
