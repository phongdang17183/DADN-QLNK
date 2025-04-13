package com.example.IotProject.component.eventListener;

import com.example.IotProject.component.event.MQTTMessageReceivedEvent;
import com.example.IotProject.dto.WebSocketDataDTO.DeviceDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WebSocketEventListener {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    @Async
    public void handleWebSocketEvent(MQTTMessageReceivedEvent event) {
        DeviceDataDTO data = new DeviceDataDTO(event.getFeedName(), event.getTimestamp(), event.getValue());
        String[] parts = event.getFeedName().split("-");
        String topic = "/topic/" + parts[1];
//        messagingTemplate.convertAndSend("/topic/messages", data);
        messagingTemplate.convertAndSend(topic, data);
    }
}
