package com.example.IotProject.dto.feedDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDTO {
    private Feed feed;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Feed {
        private String name;
    }
}
