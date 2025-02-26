package com.example.IotProject.controller;


import com.example.IotProject.service.AdafruitClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class AdafruitController {
    private final AdafruitClientService adafruitClient;

    public AdafruitController(AdafruitClientService adafruitClient) {
        this.adafruitClient = adafruitClient;
    }

    // Endpoint gửi tin nhắn MQTT
    @PostMapping("/send")
    public ResponseEntity<String> sendMqttMessage(@RequestBody String message) {
        adafruitClient.publishMessage(message);
        return ResponseEntity.ok("Message sent: " + message);
    }

    @GetMapping("/getFeeds")
    public ResponseEntity<String> getFeeds() {
        String feeds = adafruitClient.getFeeds();
        return ResponseEntity.ok(feeds);
    }
}
