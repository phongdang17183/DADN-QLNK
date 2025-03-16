package com.example.IotProject.proxy;

import com.example.IotProject.dto.feedDTO.FeedRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "adaFruitProxyHTTP", url = "${adafruit.feed.service.url}/")
public interface AdaFruitProxyHTTP {

    @GetMapping("/{username}/feeds/{feedName}")
    String getAdaFruitFeedInfo(
            @PathVariable("username") String username,
            @PathVariable("feedName") String feedName,
            @RequestHeader("X-AIO-Key") String key_AIO
    );

    @GetMapping("/{username}/feeds/{feedName}/data")
    String getAdaFruitFeedData(
            @PathVariable("username") String username,
            @PathVariable("feedName") String feedName,
            @RequestHeader("X-AIO-Key") String key_AIO
    );

    @PostMapping("/{username}/feeds")
    String createAdaFruitCreateFeed(
            @PathVariable("username") String username,
            @RequestBody FeedRequestDTO feedRequest,
            @RequestHeader("X-AIO-Key") String key_AIO
    );
}