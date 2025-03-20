package com.example.IotProject.controller;


import com.example.IotProject.model.DeviceDataModel;
import com.example.IotProject.service.DeviceDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices/data")
public class DeviceDataController {
    DeviceDataService deviceDataService;

    public DeviceDataController(DeviceDataService deviceDataService){
        this.deviceDataService = deviceDataService;
    }

    @PreAuthorize("hasRole('ROLE_Technician')")
    @GetMapping("/Alldata")
    public List<DeviceDataModel> getAlldata() {
        //bug here need fix **
        return deviceDataService.getAllDeviceData("humidity_1");
        //-------------------
    }
}
