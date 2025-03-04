package com.example.IotProject.service;




import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


@Service
public class AdafruitClientService {

    private final MessageChannel mqttOutboundChannel;
    private static final String FEEDS_URL = "https://io.adafruit.com/api/v2/{username}/feeds";
    @Value("${mqtt.username}")
    private String USERNAME;
    @Value("${mqtt.password}")
    private String PASSWORD;

    public AdafruitClientService(@Qualifier("mqttOutboundChannel") MessageChannel mqttOutboundChannel) {
        this.mqttOutboundChannel = mqttOutboundChannel;
    }

    public void publishMessage(String message) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(message).build());
    }

    public String getFeeds() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AIO-Key", PASSWORD);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(FEEDS_URL, HttpMethod.GET, entity, String.class, USERNAME);

        return response.getBody();
    }
}


