package com.example.IotProject.response.RuleResponse;

import com.example.IotProject.model.ConditionRuleModel;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RuleAndConditionResponse {
    private Long id;
    private String action;
    private String feedName;
    private Long userId;
    private List<ConditionRuleResponse> conditionRuleResponses;
}
