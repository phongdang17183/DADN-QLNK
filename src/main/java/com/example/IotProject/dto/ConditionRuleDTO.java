package com.example.IotProject.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionRuleDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("min_value")
    private String minValue;
    @JsonProperty("max_value")
    private String maxValue;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    @JsonProperty("rule_id")
    private Long ruleId;
}

