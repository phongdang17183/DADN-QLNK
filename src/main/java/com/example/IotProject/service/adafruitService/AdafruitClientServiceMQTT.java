package com.example.IotProject.service.adafruitService;




import com.example.IotProject.config.adaFruitMQTT.AdaFruitMqttConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service

public class AdafruitClientServiceMQTT {

    private final MessageHandler messageHandler;

    // Inbound
    private MqttPahoMessageDrivenChannelAdapter mqttInbound;

    // Outbound
    private MqttPahoMessageHandler mqttMessageHandler;

    AdaFruitMqttConfig adaFruitMqttConfig;

    @Autowired
    public AdafruitClientServiceMQTT(
            @Qualifier("mqttInbound") MessageProducer mqttInbound,
            @Qualifier("mqttOutbound") MessageHandler messageHandler,
            @Qualifier("mqttMessageHandler") MqttPahoMessageHandler mqttMessageHandler,
            AdaFruitMqttConfig adaFruitMqttConfig
    ) {
        this.mqttInbound = (MqttPahoMessageDrivenChannelAdapter) mqttInbound;
        this.adaFruitMqttConfig = adaFruitMqttConfig;
        this.messageHandler = messageHandler;
        this.mqttMessageHandler = mqttMessageHandler;
    }

    // TODO: Use feedkey to generate topic name and subscribe to that topic
    // Inbound
    public void listenToFeed(String feedKey) {
        String userName = adaFruitMqttConfig.getUSERNAME();
        String topic = userName + "/feeds/" + feedKey;
        mqttInbound.addTopic(topic);
    }

    // Outbound
    private void updateTopic(String newTopic) {
        mqttMessageHandler.setDefaultTopic(newTopic);
    }

    // Outbound
    public void publishMessage(Float message, String topic) {
        String userName = adaFruitMqttConfig.getUSERNAME();
        topic = userName + "/feeds/" + topic;

        updateTopic(topic);


        messageHandler.handleMessage(MessageBuilder.withPayload(message.toString()).build());
    }




//    // Outbound
//    public String getCurrentTopic() {
//        return mqttMessageHandler
//    }
}


