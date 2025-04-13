package com.example.IotProject.response.RuleResponse;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RuleResponse {
    private Long id;
    private String action;
    private String feedName;
    private Long userId;
}
