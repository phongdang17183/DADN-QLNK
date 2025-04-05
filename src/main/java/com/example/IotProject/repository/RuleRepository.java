package com.example.IotProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IotProject.model.RuleModel;

public interface RuleRepository extends JpaRepository<RuleModel, Long> {
    RuleModel findAllById(Long id); // Tìm kiếm theo ID
}