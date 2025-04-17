package com.example.IotProject.service.FCMservice;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    /**
     * Gửi thông báo đến thiết bị với token được chỉ định.
     * @param deviceToken Token của thiết bị
     * @param title Tiêu đề của thông báo
     * @param body Nội dung của thông báo
     * @return String phản hồi từ Firebase (ID thông báo, v.v.)
     */
    public static String sendPushNotification(String deviceToken, String title, String body) {
        // Xây dựng thông báo
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .setToken(deviceToken)
                .build();

        try {
            // Gửi thông báo qua FirebaseMessaging
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
            return response;
        } catch (FirebaseMessagingException e) {
            System.err.println("Error sending Firebase message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
