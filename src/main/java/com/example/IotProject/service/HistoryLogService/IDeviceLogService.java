package com.example.IotProject.service.HistoryLogService;

import java.sql.Timestamp;
import java.util.List;

import com.example.IotProject.model.DeviceLogModel;

public interface IDeviceLogService {
    public List<DeviceLogModel> getDeviceLogByDeviceId(String feedname );
    public List<DeviceLogModel> getAll();
    public DeviceLogModel createDeviceLog(String action, String feedName, Timestamp timestamp);
    
}
