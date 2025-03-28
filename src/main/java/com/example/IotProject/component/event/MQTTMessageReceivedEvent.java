package com.example.IotProject.component.event;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MQTTMessageReceivedEvent {
    private String feedName;
    private Timestamp timestamp;
    private Float value;

    public MQTTMessageReceivedEvent(String feedName, Timestamp timestamp, Float value) {
        this.feedName = feedName;
        this.timestamp = timestamp;
        this.value = value;
    }
}
