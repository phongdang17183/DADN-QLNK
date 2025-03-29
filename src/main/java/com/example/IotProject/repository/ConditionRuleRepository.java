package com.example.IotProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IotProject.model.ConditionRuleModel;

public interface ConditionRuleRepository extends JpaRepository<ConditionRuleModel, Long> {
    ConditionRuleModel findAllById(Long id); // Tìm kiếm theo ID
}
