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

import java.util.ArrayList;
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
        List<HistoryLogResponse> histories = new ArrayList<>();

        // Lấy dữ liệu từ các service
        List<HistoryLogModel> historyLogModels = historyLogService.getAll();
        List<DeviceLogModel> deviceLogModels = deviceLogService.getAll();

        // Convert từ HistoryLogModel sang HistoryLogResponse
        for (HistoryLogModel historyLogModel : historyLogModels) {
            histories.add(HistoryLogResponse.builder()
                    .username(historyLogModel.getUser().getUsername())
                    .action(historyLogModel.getAction())
                    .timestamp(historyLogModel.getTimestamp())
                    .build());
        }

        // Convert từ DeviceLogModel sang HistoryLogResponse
        for (DeviceLogModel deviceLogModel : deviceLogModels) {
            histories.add(HistoryLogResponse.builder()
                    .device(deviceLogModel.getDevice().getFeedName())
                    .action(deviceLogModel.getAction())
                    .timestamp(deviceLogModel.getTimestamp())
                    .build());
        }

        // Sắp xếp theo timestamp giảm dần
        histories.sort((h1, h2) -> h2.getTimestamp().compareTo(h1.getTimestamp()));

        return ResponseEntity.ok(histories);
    }



}
