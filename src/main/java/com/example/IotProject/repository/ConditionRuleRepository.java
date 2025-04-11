package com.example.IotProject.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.IotProject.enums.RuleOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.IotProject.model.ConditionRuleModel;

public interface ConditionRuleRepository extends JpaRepository<ConditionRuleModel, Long> {
    ConditionRuleModel findAllById(Long id);
    List<ConditionRuleModel> findAll();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ConditionRuleModel c WHERE c.name = :name AND c.minValue = :min_value AND c.maxValue = :max_value AND c.startDate = :start_date AND c.endDate = :end_date")
    boolean existsByMinValueAndMaxValueAndStartDateAndEndDate(
        @Param("name") String name, 
        @Param("min_value") String minValue,
        @Param("max_value") String maxValue,
        @Param("start_date") LocalDateTime startDate,
        @Param("end_date") LocalDateTime endDate); 


    @Query("SELECT c FROM ConditionRuleModel c WHERE c.rule.id = :ruleId")
    List<ConditionRuleModel> findByRuleId(@Param("ruleId") Long ruleId);
}
