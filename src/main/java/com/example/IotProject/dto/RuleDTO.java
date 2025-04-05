package com.example.IotProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@AllArgsConstructor
@NoArgsConstructor
public class RuleDTO {
    private String action;
    private String feedName;
    private Long userId;
}
