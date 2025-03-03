package com.example.IotProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IotProject.service.HistoryLogService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/history-logs")
public class HistoryLogController {
    private final HistoryLogService historyLogService;

    public HistoryLogController(HistoryLogService historyLogService) {
        this.historyLogService = historyLogService;
    }

    @GetMapping("/getHistoryLog")
    public String getHistoryLog() {
        return this.historyLogService.geHistoryLogById(1L).getId() + " "
                + this.historyLogService.geHistoryLogById(1L).getAction();
    }

}
