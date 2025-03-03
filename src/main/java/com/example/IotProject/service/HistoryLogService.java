package com.example.IotProject.service;

import org.springframework.stereotype.Service;

import com.example.IotProject.model.HistoryLog;
import com.example.IotProject.repository.HistoryLogRepository;

@Service
public class HistoryLogService implements IHistoryLogService {

    private HistoryLogRepository historyLogRepository;

    public HistoryLogService(HistoryLogRepository historyLogRepository) {
        this.historyLogRepository = historyLogRepository;
    }

    @Override
    public HistoryLog geHistoryLogById(Long id) {
        return historyLogRepository.findById(id).orElse(null);
    }

}
