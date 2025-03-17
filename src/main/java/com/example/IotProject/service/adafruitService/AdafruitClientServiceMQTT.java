package com.example.IotProject.service.adafruitService;




import com.example.IotProject.config.adaFruitMQTT.AdaFruitMqttConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class AdafruitClientServiceMQTT {

    private final MessageChannel mqttOutboundChannel;

    MqttPahoMessageDrivenChannelAdapter mqttInbound;

    AdaFruitMqttConfig adaFruitMqttConfig;

    @Autowired
    public AdafruitClientServiceMQTT(
            @Qualifier("mqttOutboundChannel") MessageChannel mqttOutboundChannel,
            @Qualifier("mqttInbound") MessageProducer mqttInbound,
            AdaFruitMqttConfig adaFruitMqttConfig
    ) {
        this.mqttOutboundChannel = mqttOutboundChannel;
        this.mqttInbound = (MqttPahoMessageDrivenChannelAdapter) mqttInbound;
        this.adaFruitMqttConfig = adaFruitMqttConfig;
    }

    public void publishMessage(String message) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(message).build());
    }

    // TODO: Use feedkey to generate topic name and subscribe to that topic
    public void listenToFeed(String feedKey) {
        String userName = adaFruitMqttConfig.getUSERNAME();
        String topic = userName + "/feeds/" + feedKey;
        mqttInbound.addTopic(topic);
    }
}


