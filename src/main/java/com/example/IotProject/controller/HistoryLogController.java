package com.example.IotProject.controller;

import com.example.IotProject.model.DeviceLogModel;
import com.example.IotProject.model.HistoryLogModel;
import com.example.IotProject.response.HistoryLog.HistoryLogResponse;
import com.example.IotProject.service.HistoryLogService.DeviceLogService;
import com.example.IotProject.service.HistoryLogService.IDeviceLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.service.HistoryLogService.HistoryLogService;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history-logs")
public class HistoryLogController {
    private final HistoryLogService historyLogService;
    private final IDeviceLogService deviceLogService;

    public HistoryLogController(HistoryLogService historyLogService, DeviceLogService deviceLogService) {

        this.historyLogService = historyLogService;
        this.deviceLogService = deviceLogService;
    }

    @GetMapping("")
    public ResponseEntity<List<HistoryLogResponse>> getHistoryLog() {
        List<HistoryLogResponse> histories = new java.util.ArrayList<>(List.of());
        List<HistoryLogModel> historyLogModels = historyLogService.getAll();
        List<DeviceLogModel> deviceLogModels = deviceLogService.getAll();
        for(HistoryLogModel historyLogModel : historyLogModels){
            histories.add(HistoryLogResponse.builder()
                            .username(historyLogModel.getUser().getUsername())
                            .action(historyLogModel.getAction())
                            .timestamp(historyLogModel.getTimestamp())
                            .build());
        }
        for(DeviceLogModel deviceLogModel : deviceLogModels){
            histories.add(HistoryLogResponse.builder()
                    .device(deviceLogModel.getDevice().getFeedName())
                    .action(deviceLogModel.getAction())
                    .timestamp(deviceLogModel.getTimestamp())
                    .build());
        }
        return ResponseEntity.ok(histories);
    }


}
