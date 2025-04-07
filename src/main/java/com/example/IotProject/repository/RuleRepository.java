package com.example.IotProject.repository;

import com.example.IotProject.model.RuleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleModel, Long> {
    RuleModel findAllById(Long id);
}
