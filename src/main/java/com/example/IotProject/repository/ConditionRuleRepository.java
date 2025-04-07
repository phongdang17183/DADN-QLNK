package com.example.IotProject.repository;

import java.util.List;

import com.example.IotProject.enums.RuleOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.IotProject.model.ConditionRuleModel;

public interface ConditionRuleRepository extends JpaRepository<ConditionRuleModel, Long> {
    ConditionRuleModel findAllById(Long id);
    List<ConditionRuleModel> findAll();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ConditionRuleModel c WHERE c.name = :name AND c.relational_operator = :relational_operator AND c.value = :value")
    boolean existsByNameAndRelationalOperatorAndValue(@Param("name") String name,
                                                        @Param("relational_operator") RuleOperator relational_operator,
                                                        @Param("value") String value);

    @Query("SELECT c FROM ConditionRuleModel c WHERE c.rule.id = :ruleId")
    List<ConditionRuleModel> findByRuleId(@Param("ruleId") Long ruleId);
}
