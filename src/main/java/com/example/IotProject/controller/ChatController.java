package com.example.IotProject.controller;

import com.example.IotProject.dto.WebSocketDataDTO.DeviceDataDTO;
import com.example.IotProject.repository.HistoryLogRepository;
import com.example.IotProject.service.HistoryLogService;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    AdafruitClientServiceMQTT adafruitClientServiceMQTT;
    HistoryLogService historyLogService;

    public ChatController(AdafruitClientServiceMQTT adafruitClientServiceMQTT, HistoryLogService historyLogService) {
        this.adafruitClientServiceMQTT = adafruitClientServiceMQTT;
        this.historyLogService = historyLogService;
    }

    @MessageMapping("/chat") // Client gửi đến "/app/chat" -> gửi đề điều khuyển thiết bị
    public DeviceDataDTO handleChatMessage(DeviceDataDTO message, Principal principal) {
        String username = principal.getName(); // 👈 lấy username tại đây
        System.out.println("---------websocket receive--------");
        System.out.println(username);
        System.out.println(message);
        adafruitClientServiceMQTT.publishMessage(message.getValue(), message.getFeedName());
        String action;
        if(message.getValue() == 2.0 || message.getValue() == 4.0  ){
            action = "ON/" + message.getFeedName();
        } else{
            action = "OFF/" + message.getFeedName();
        }
        historyLogService.logWSHistory(action,username,message.getTimestamp());
        return message;
    }
}