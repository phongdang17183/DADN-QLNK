package com.example.IotProject.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private MessageScheduler messageScheduler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        messageScheduler.addSession(session);
        System.out.println("Kết nối mới: " + session.getId());
        session.sendMessage(new TextMessage("Kết nối thành công!"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Tin nhắn từ client: " + payload);
        session.sendMessage(new TextMessage("Server: " + payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        messageScheduler.removeSession(session);
        System.out.println("Đã ngắt kết nối: " + session.getId());
    }
}