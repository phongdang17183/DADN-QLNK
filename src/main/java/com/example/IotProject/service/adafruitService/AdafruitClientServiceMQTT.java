package com.example.IotProject.service.adafruitService;
import com.example.IotProject.component.event.MQTTMessageReceivedEvent;
import com.example.IotProject.config.adaFruitMQTT.AdaFruitMqttConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service

public class AdafruitClientServiceMQTT {

    // Inbound
    private MqttPahoMessageDrivenChannelAdapter mqttInbound;

    // Outbound
    private MqttPahoMessageHandler mqttOutbound;

    AdaFruitMqttConfig adaFruitMqttConfig;
    ApplicationEventPublisher eventPublisher;

    @Autowired
    public AdafruitClientServiceMQTT(
            @Qualifier("mqttInbound") MessageProducer mqttInbound,
            @Qualifier("mqttOutbound") MqttPahoMessageHandler mqttOutbound,
            AdaFruitMqttConfig adaFruitMqttConfig,
            ApplicationEventPublisher eventPublisher
    ) {
        this.mqttInbound = (MqttPahoMessageDrivenChannelAdapter) mqttInbound;
        this.mqttOutbound = mqttOutbound;
        this.adaFruitMqttConfig = adaFruitMqttConfig;
        this.eventPublisher = eventPublisher;
    }

    // TODO: Use feedkey to generate topic name and subscribe to that topic
    // Inbound
    public void listenToFeed(String feedKey) {
        String userName = adaFruitMqttConfig.getUSERNAME();
        String topic = userName + "/feeds/" + feedKey;
        mqttInbound.addTopic(topic);
    }

    //Inbound
    @Async
    public void processMessage(Message<?> message){
        System.out.println("Received message details----------------------------------------------------------");
        System.out.println("Payload: " + message.getPayload());
        System.out.println("Headers: " + message.getHeaders());

        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        int index = topic.lastIndexOf("/");
        if(index != -1 && index < topic.length() - 1) {
            String feedName = topic.substring(index + 1);
            Float value = Float.parseFloat(message.getPayload().toString());
            Timestamp now = new Timestamp(System.currentTimeMillis());

            MQTTMessageReceivedEvent event = new MQTTMessageReceivedEvent(feedName, now, value);
            eventPublisher.publishEvent(event);

        } else {
            System.out.println("Invalid topic format: " + topic);
        }
    }

    // Outbound
    private void updateTopic(String newTopic) {
        mqttOutbound.setDefaultTopic(newTopic);
    }

    // Outbound
    public void publishMessage(Float message, String topic) {
        String userName = adaFruitMqttConfig.getUSERNAME();
        topic = userName + "/feeds/" + topic;
        updateTopic(topic);
        Long value = message.longValue();
        mqttOutbound.handleMessage(MessageBuilder.withPayload(value.toString()).build());
    }


}


