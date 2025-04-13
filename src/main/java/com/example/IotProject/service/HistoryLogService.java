package com.example.IotProject.service;

import com.example.IotProject.model.UserModel;
import org.springframework.stereotype.Service;

import com.example.IotProject.model.HistoryLogModel;
import com.example.IotProject.repository.HistoryLogRepository;

import java.sql.Timestamp;

@Service
public class HistoryLogService implements IHistoryLogService {

    private final HistoryLogRepository historyLogRepository;
    private final UserService userService;


    public HistoryLogService(HistoryLogRepository historyLogRepository, UserService userService) {
        this.historyLogRepository = historyLogRepository;
        this.userService = userService;
    }

    @Override
    public HistoryLogModel geHistoryLogById(Long id) {
        return historyLogRepository.findById(id).orElse(null);
    }

    public String logWSHistory(String action, String username, Timestamp time){
        UserModel user = userService.getUserByUsername(username);
        HistoryLogModel log = new HistoryLogModel();
        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(time);
        historyLogRepository.save(log);
        return "Log Action Success";
    }
}
