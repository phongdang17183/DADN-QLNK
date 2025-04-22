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
    public List<DeviceDataResponse> getDataOneDay(@RequestParam("feedName") String feedName) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getDataOneDay(feedName).stream().map(
                                                deviceDataModel -> DeviceDataResponse.builder()
                                                        .timestamp(deviceDataModel.getTime())
                                                        .value(deviceDataModel.getValue())
                                                        .device(deviceDataModel.getDevice().getFeedName())
                                                        .build()
                                                    ).toList();
        return deviceDataResponses;
    }

    // Lấy dữ liệu 1 tuần
    @GetMapping("/oneweek")
    public List<DeviceDataResponse> getDataOneWeek(@RequestParam("feedName") String feedName) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getDataOneWeek(feedName).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .timestamp(deviceDataModel.getTime())
                        .value(deviceDataModel.getValue())
                        .device(deviceDataModel.getDevice().getFeedName())
                        .build()
        ).toList();
        return deviceDataResponses;
    }

    // Lấy dữ liệu 1 tháng
    @GetMapping("/onemonth")
    public List<DeviceDataResponse> getDataByZoneOneMonth(@RequestParam("feedName") String feedName) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getDataOneMonth(feedName).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .timestamp(deviceDataModel.getTime())
                        .value(deviceDataModel.getValue())
                        .device(deviceDataModel.getDevice().getFeedName())
                        .build()
        ).toList();
        return deviceDataResponses;
    }

    // Lấy dữ liệu 1 ngày
    @GetMapping("/datazoneday")
    public List<DeviceDataResponse> getDataByZoneOneDay(@RequestParam("ZoneId") Long ZoneId) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getDataByZoneOneDay(ZoneId).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .timestamp(deviceDataModel.getTime())
                        .value(deviceDataModel.getValue())
                        .device(deviceDataModel.getDevice().getFeedName())
                        .build()
        ).toList();
        return deviceDataResponses;
    }

    // Lấy dữ liệu 1 tuần
    @GetMapping("/datazoneweek")
    public List<DeviceDataResponse> getDataByZoneOneWeek(@RequestParam("ZoneId") Long ZoneId) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getDataByZoneOneWeek(ZoneId).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .timestamp(deviceDataModel.getTime())
                        .value(deviceDataModel.getValue())
                        .device(deviceDataModel.getDevice().getFeedName())
                        .build()
        ).toList();
        return deviceDataResponses;
    }

    // Lấy dữ liệu 1 tháng
    @GetMapping("/datazonemonth")
    public List<DeviceDataResponse> getDataOneMonth(@RequestParam("ZoneId") Long ZoneId) {
        List<DeviceDataResponse> deviceDataResponses = deviceDataService.getDataByZoneOneMonth(ZoneId).stream().map(
                deviceDataModel -> DeviceDataResponse.builder()
                        .timestamp(deviceDataModel.getTime())
                        .value(deviceDataModel.getValue())
                        .device(deviceDataModel.getDevice().getFeedName())
                        .build()
        ).toList();
        return deviceDataResponses;
    }
}
