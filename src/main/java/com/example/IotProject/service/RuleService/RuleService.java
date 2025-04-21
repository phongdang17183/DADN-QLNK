package com.example.IotProject.service.RuleService;

import java.util.List;

import com.example.IotProject.model.ConditionRuleModel;
import com.example.IotProject.service.DeviceService.IDeviceService;
import com.example.IotProject.service.UserService.IUserService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.IotProject.dto.RuleDTO;
import com.example.IotProject.model.RuleModel;
import com.example.IotProject.repository.RuleRepository;

@Service
public class RuleService implements IRuleService {

    private final RuleRepository ruleRepository;
    private final IDeviceService deviceService;
    private final IUserService userService;

    public RuleService(RuleRepository ruleRepository, IDeviceService deviceService, IUserService userService) {
        this.ruleRepository = ruleRepository;
        this.deviceService = deviceService;
        this.userService = userService;
    }
    @Override
    public RuleModel createRule(RuleDTO ruleDTO) {
        // Logic to create a rule
        RuleModel ruleModel = new RuleModel();
        ruleModel.setAction(ruleDTO.getAction());
        ruleModel.setDevice(deviceService.findByFeed(ruleDTO.getFeedName()));
        ruleModel.setUser(userService.getUserById(ruleDTO.getUserId()));
        return ruleRepository.save(ruleModel);
    }
    @Override
    public void updateRule(Long id ,RuleDTO ruleDTO ) {
        // Logic to update a rule
        RuleModel ruleModel = ruleRepository.findAllById(id);
        if (ruleModel != null) {
            ruleModel.setAction(ruleDTO.getAction());
            ruleModel.setDevice(deviceService.findByFeed(ruleDTO.getFeedName()));
            ruleModel.setUser(userService.getUserById(ruleDTO.getUserId()));
            ruleRepository.save(ruleModel);
        } else {
            throw new RuntimeException("Rule not found with id: " + id);
        }

    }
    @Override
    public void deleteRule(Long id) {
        // Logic to delete a rule
        RuleModel ruleModel = ruleRepository.findAllById(id);
        if (ruleModel != null) {

            ruleRepository.delete(ruleModel);
        } else {
            throw new RuntimeException("Rule not found with id: " + id);
        }
    }
    @Override
    public RuleModel getRuleById(Long id) {
        // Logic to get a rule by ID
        RuleModel ruleModel = ruleRepository.findAllById(id);
        if (ruleModel != null) {
            return ruleModel;
        } else {
            throw new RuntimeException("Rule not found with id: " + id);
        }
    }
    @Override
    public List<RuleModel> getAllRules() {
        return ruleRepository.findAll();
    }
    @Override
    public List<RuleModel> getRulesbyDevice(String feedname){
        List<RuleModel> rules = ruleRepository.findRulesByDeviceId(feedname);

        if (rules == null || rules.isEmpty()) {
            System.out.println("No rule found with device_id = " + feedname);
//            throw new RuntimeException("No rule found with device_id = " + feedname);
        }
        return rules;
    }

    @Override
    public List<RuleModel> getRuleByActuatorNameAndZoneId(String actuatorName, Long zoneId) {
        String actuatorNameAndZoneId = actuatorName + '-' + zoneId.toString();
        List<RuleModel> rules = ruleRepository.findRulesByActuatorNameAndZoneId(actuatorNameAndZoneId);

        if (rules == null || rules.isEmpty()) {
            throw new RuntimeException("No rule found with actuatorName = " + actuatorName + " and zoneId = " + zoneId);
        }

        return rules;
    }

}
