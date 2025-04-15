package com.example.IotProject.service.HistoryLogService;

import com.example.IotProject.model.DeviceLogModel;
import com.example.IotProject.repository.DeviceLogRepository;
import com.example.IotProject.service.DeviceService.DeviceService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DeviceLogService {
    private final DeviceLogRepository deviceLogRepository;
    private final DeviceService deviceService;

    public DeviceLogService(DeviceLogRepository deviceLogRepository, DeviceService deviceService){
        this.deviceLogRepository = deviceLogRepository;
        this.deviceService = deviceService;
    }

    public List<DeviceLogModel> getDeviceLogByDeviceId(String feedname ){
        return deviceLogRepository.findByDeviceId(feedname);
    }

    public List<DeviceLogModel> getAll(){
        return deviceLogRepository.findAll();
    }

    public DeviceLogModel createDeviceLog(String action, String feedName, Timestamp timestamp){
        DeviceLogModel deviceLogModel = DeviceLogModel.builder()
                .device(deviceService.findByFeed(feedName))
                .action(action)
                .timestamp(timestamp)
                .build();

        return deviceLogRepository.save(deviceLogModel);
    }
}
