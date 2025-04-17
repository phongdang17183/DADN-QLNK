package com.example.IotProject.controller;


import com.example.IotProject.model.DeviceDataModel;
import com.example.IotProject.response.DeviceDataResponse;
import com.example.IotProject.service.DeviceDataService.IDeviceDataService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices/data")
public class DeviceDataController {
    IDeviceDataService deviceDataService;

    public DeviceDataController(IDeviceDataService deviceDataService){
        this.deviceDataService = deviceDataService;
    }

    @PreAuthorize("hasRole('ROLE_Technician')")
    @GetMapping("/Alldata")
    public List<DeviceDataModel> getAlldata() {
        //bug here need fix **
        return deviceDataService.getAllDeviceData("light-1");
        //-------------------
    }

    @GetMapping("/Alldata/zone/{zoneId}")
    public ResponseEntity<List<DeviceDataResponse>> getDataByZone(@PathVariable Long zoneId) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getAllZoneData(zoneId).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .device(deviceDataModel.getDevice().getFeedName())
                        .value(deviceDataModel.getValue())
                        .timestamp(deviceDataModel.getTime())
                        .build()
        ).toList();
        return ResponseEntity.ok(deviceDataResponses);
        //-------------------
    }

    @GetMapping("/Alldata/device/{feedName}")
    public ResponseEntity<List<DeviceDataResponse>> getDataByDevice(@PathVariable String feedName) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getAllDeviceData(feedName).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .device(deviceDataModel.getDevice().getFeedName())
                        .value(deviceDataModel.getValue())
                        .timestamp(deviceDataModel.getTime())
                        .build()
        ).toList();
        return ResponseEntity.ok(deviceDataResponses);
    }

    // Lấy dữ liệu 1 ngày
    @GetMapping("/oneday")
    public List<DeviceDataModel> getDataOneDay(@RequestParam("feedName") String feedName) {
        return deviceDataService.getDataOneDay(feedName);
    }

    // Lấy dữ liệu 1 tuần
    @GetMapping("/oneweek")
    public List<DeviceDataModel> getDataOneWeek(@RequestParam("feedName") String feedName) {
        return deviceDataService.getDataOneWeek(feedName);
    }

    // Lấy dữ liệu 1 tháng
    @GetMapping("/onemonth")
    public List<DeviceDataModel> getDataOneMonth(@RequestParam("feedName") String feedName) {
        return deviceDataService.getDataOneMonth(feedName);
    }
}
