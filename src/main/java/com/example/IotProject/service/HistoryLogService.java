package com.example.IotProject.service;

import org.springframework.stereotype.Service;

import com.example.IotProject.model.HistoryLogModel;
import com.example.IotProject.repository.HistoryLogRepository;

@Service
public class HistoryLogService implements IHistoryLogService {

    private final HistoryLogRepository historyLogRepository;

    public HistoryLogService(HistoryLogRepository historyLogRepository) {
        this.historyLogRepository = historyLogRepository;
    }

    @Override
    public HistoryLogModel geHistoryLogById(Long id) {
        return historyLogRepository.findById(id).orElse(null);
    }

}
