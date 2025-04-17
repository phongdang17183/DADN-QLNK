package com.example.IotProject.component.eventListener;

import com.example.IotProject.component.event.MQTTMessageReceivedEvent;
import com.example.IotProject.service.DeviceDataService.IDeviceDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DatabaseEventListener {
    @Autowired
    private IDeviceDataService deviceDataService;

    @EventListener
    @Async
    public void handleDatabaseEvent(MQTTMessageReceivedEvent event) {
        deviceDataService.saveData(event.getTimestamp(), event.getValue(), event.getFeedName());
    }


}
