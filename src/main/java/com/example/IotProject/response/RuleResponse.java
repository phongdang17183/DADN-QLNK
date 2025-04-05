package com.example.IotProject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleResponse {
    private Long id;
    private String action;
    private String feedName;
    private Long userId;
}
