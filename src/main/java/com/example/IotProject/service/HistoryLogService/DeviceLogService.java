package com.example.IotProject.service.HistoryLogService;

import com.example.IotProject.model.DeviceLogModel;
import com.example.IotProject.repository.DeviceLogRepository;
import com.example.IotProject.service.DeviceService.IDeviceService;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DeviceLogService implements IDeviceLogService {
    private final DeviceLogRepository deviceLogRepository;
    private final IDeviceService deviceService;

    public DeviceLogService(DeviceLogRepository deviceLogRepository, IDeviceService deviceService){
        this.deviceLogRepository = deviceLogRepository;
        this.deviceService = deviceService;
    }

    @Override
    public List<DeviceLogModel> getDeviceLogByDeviceId(String feedname ){
        return deviceLogRepository.findByDeviceId(feedname);
    }
    @Override
    public List<DeviceLogModel> getAll(){
        return deviceLogRepository.findAll();
    }
    @Override
    public DeviceLogModel createDeviceLog(String action, String feedName, Timestamp timestamp){
        DeviceLogModel deviceLogModel = DeviceLogModel.builder()
                .device(deviceService.findByFeed(feedName))
                .action(action)
                .timestamp(timestamp)
                .build();

        return deviceLogRepository.save(deviceLogModel);
    }
}
