package com.example.IotProject.service.DeviceDataService;

import java.sql.Timestamp;
import java.util.List;

import com.example.IotProject.model.DeviceDataModel;

public interface IDeviceDataService {
    public DeviceDataModel saveData(Timestamp time, Float value, String feedname);
    public List<DeviceDataModel> getAllDeviceData(String feedName);
    public List<DeviceDataModel> getAllZoneData(Long zoneId);
    public List<DeviceDataModel> getDataOneDay(String feedName);
    public List<DeviceDataModel> getDataOneWeek(String feedName);
    public List<DeviceDataModel> getDataOneMonth(String feedName);
}