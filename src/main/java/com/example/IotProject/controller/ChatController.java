package com.example.IotProject.controller;

import com.example.IotProject.dto.WebSocketDataDTO.DeviceDataDTO;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    AdafruitClientServiceMQTT adafruitClientServiceMQTT;


    public ChatController(AdafruitClientServiceMQTT adafruitClientServiceMQTT) {
        this.adafruitClientServiceMQTT = adafruitClientServiceMQTT;
    }

    @MessageMapping("/chat") // Client gửi đến "/app/chat"
    @SendTo("/topic/messages") // Server gửi đến "/topic/messages"
    public DeviceDataDTO handleChatMessage(DeviceDataDTO message) {
        // define message from FE
        //

        System.out.println(message);

        adafruitClientServiceMQTT.publishMessage(message.getValue(), message.getFeedName());




        return message;
    }
}