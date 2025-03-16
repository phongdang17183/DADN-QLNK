package com.example.IotProject.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

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
}