package com.example.IotProject.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceDataResponse {
    private String device;
    private Float value;
    private Timestamp timestamp;
}
