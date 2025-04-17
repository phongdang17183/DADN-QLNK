package com.example.IotProject.service.HistoryLogService;

import com.example.IotProject.model.UserModel;
import com.example.IotProject.service.UserService.IUserService;
import org.springframework.stereotype.Service;

import com.example.IotProject.model.HistoryLogModel;
import com.example.IotProject.repository.HistoryLogRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HistoryLogService implements IHistoryLogService {

    private final HistoryLogRepository historyLogRepository;
    private final IUserService userService;


    public HistoryLogService(HistoryLogRepository historyLogRepository, IUserService userService) {
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

    public List<HistoryLogModel> getAll(){
        return historyLogRepository.findAll();
    }
}
