package com.example.IotProject.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionRuleDTO {
    private String name;
    private String minValue;
    private String maxValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long ruleId;
}

