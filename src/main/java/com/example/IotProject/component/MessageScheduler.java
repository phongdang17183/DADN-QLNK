package com.example.IotProject.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@EnableScheduling
public class MessageScheduler {

    private final List<WebSocketSession> sessions = new ArrayList<>();

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void sendPeriodicMessage(String message) {
        message = "Tin nhắn tự động từ server: " + message;
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}