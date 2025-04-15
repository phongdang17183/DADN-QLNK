package com.example.IotProject.response.HistoryLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceLogResponse {
    private String action;
    private String device;
    private Timestamp timestamp;
}
