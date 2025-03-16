package com.example.IotProject.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

@Configuration
@EnableFeignClients(basePackages = "com.example.IotProject.proxy")
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    private String BROKER_URL;

    @Value("${mqtt.username}")
    private String USERNAME;

    @Value("${mqtt.password}")
    private String PASSWORD;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{BROKER_URL});
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        factory.setConnectionOptions(options);
        System.out.println("Connected success");
        return factory;
    }
}
