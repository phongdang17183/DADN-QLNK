package com.example.IotProject.controller;


import com.example.IotProject.service.FCMservice.FCMService;
import com.example.IotProject.service.adafruitService.AdaFruitClientServiceHTTP;
import com.example.IotProject.service.adafruitService.AdafruitClientServiceMQTT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/mqtt")
// This is just a testing AdaFruit Controller
public class AdafruitController {
    private final AdafruitClientServiceMQTT adaFruitServiceMQTT;
    private final AdaFruitClientServiceHTTP adaFruitServiceHTTP;

    @Autowired
    public AdafruitController(AdafruitClientServiceMQTT adaFruitServiceMQTT, AdaFruitClientServiceHTTP adaFruitServiceHTTP) {
        this.adaFruitServiceMQTT = adaFruitServiceMQTT;
        this.adaFruitServiceHTTP = adaFruitServiceHTTP;
    }

    // Endpoint gửi tin nhắn MQTT
    @PreAuthorize("hasRole('ROLE_Technician')")
    @PostMapping("/send")
    public ResponseEntity<String> sendMqttMessage(@RequestBody String message) {
//        adaFruitServiceMQTT.publishMessage(message);
        return ResponseEntity.ok("Message sent: " + message);
    }

    @PreAuthorize("hasRole('ROLE_Technician')")
    @GetMapping("/{username}/{feedName}/getFeeds")
    public ResponseEntity<String> getFeeds(@PathVariable String username, @PathVariable String feedName) {
        String feeds = adaFruitServiceHTTP.getFeedInfo(username, feedName);
        return ResponseEntity.ok(feeds);
    }

    @PostMapping("/sendnoti")
    public String sendNotification(@RequestParam String token,
                                   @RequestParam String title,
                                   @RequestParam String body) {
        return FCMService.sendPushNotification(token, title, body);
    }
}
