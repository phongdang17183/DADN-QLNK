package com.example.IotProject.config.adaFruitMQTT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.integration.annotation.ServiceActivator;

@Configuration
@IntegrationComponentScan
public class MqttInboundConfig {

    @Value("${mqtt.client.id}")
    private String CLIENT_ID_INBOUND;

    @Value("${mqtt.subscribe.topic}")
    private String SUBSCRIBE_TOPIC;


    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer mqttInbound(MqttPahoClientFactory mqttClientFactory) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(CLIENT_ID_INBOUND, mqttClientFactory);


        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    // Xử lý tin nhắn nhận được
    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handler() {
        return message -> {
            System.out.println("Received message details:");
            System.out.println("Payload: " + message.getPayload());
            System.out.println("Headers: " + message.getHeaders());

            // Nếu muốn lấy thông tin cụ thể, ví dụ topic:
            Object topic = message.getHeaders().get("mqtt_receivedTopic");
            System.out.println("Topic: " + topic);
        };
    }
}

