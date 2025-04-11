package com.example.IotProject.response;

import java.time.LocalDateTime;

import lombok.*;

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
