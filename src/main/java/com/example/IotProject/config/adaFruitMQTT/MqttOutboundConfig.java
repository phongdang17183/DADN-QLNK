package com.example.IotProject.config.adaFruitMQTT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

@Configuration
public class MqttOutboundConfig {
//    @Value("${mqtt.client.id}")
//    private String CLIENT_ID;

//    @Value("${mqtt.subscribe.topic}")
//    private String DEFAULT_TOPIC;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler mqttOutbound(MqttPahoClientFactory mqttClientFactory) {
        String CLIENT_ID = "mqtt-handler" ;
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(CLIENT_ID, mqttClientFactory);
        handler.setAsync(true);
//        handler.setDefaultTopic(DEFAULT_TOPIC);
        handler.setConverter(new DefaultPahoMessageConverter());
        return handler;
    }

}