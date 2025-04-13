package com.example.IotProject.response.RuleResponse;

import com.example.IotProject.enums.RuleOperator;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ConditionRuleResponse {
    private Long id;
    private String name;
    private String minValue;
    private String maxValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
