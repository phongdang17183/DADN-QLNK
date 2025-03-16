package com.example.IotProject.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat") // Client gửi đến "/app/chat"
    @SendTo("/topic/messages") // Server gửi đến "/topic/messages"
    public String handleChatMessage(String message) {
        return "Server received: " + message;
    }
}