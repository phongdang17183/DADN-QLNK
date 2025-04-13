package com.example.IotProject.service;

import com.example.IotProject.model.DeviceDataModel;
import com.example.IotProject.model.DeviceModel;
import com.example.IotProject.repository.DeviceDataRepository;
import com.example.IotProject.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DeviceDataService {
    private final DeviceDataRepository deviceDataRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceDataService(
            DeviceDataRepository deviceDataRepository,
            DeviceRepository deviceRepository
    ) {
        this.deviceDataRepository = deviceDataRepository;
        this.deviceRepository = deviceRepository;
    }

    public DeviceDataModel saveData(Timestamp time, Float value, String feedname){
        DeviceModel device = deviceRepository.findByFeedName(feedname);

        DeviceDataModel newData = new DeviceDataModel();
        newData.setTime(time);
        newData.setValue(value);
        newData.setDevice(device);
        return deviceDataRepository.save(newData);
    }

    public List<DeviceDataModel> getAllDeviceData(String feedName){
        return deviceDataRepository.findByDevice_feedName(feedName);
    }

    public List<DeviceDataModel> getAllZoneData(Long zoneId){
        List<DeviceModel> devices = deviceRepository.findByZoneId(zoneId);

        return deviceDataRepository.findByDevice_feedName("feedName");
    }

}
