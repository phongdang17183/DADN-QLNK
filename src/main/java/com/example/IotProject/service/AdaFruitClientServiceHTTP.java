package com.example.IotProject.service;

import com.example.IotProject.proxy.AdaFruitProxyHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdaFruitClientServiceHTTP {

    private final AdaFruitProxyHTTP adaFruitProxy;

    private final String AIOKey;

    @Autowired
    public AdaFruitClientServiceHTTP(AdaFruitProxyHTTP adaFruitProxy, @Value("${mqtt.password}") String AIOKey) {
        this.adaFruitProxy = adaFruitProxy;
        this.AIOKey = AIOKey;
    }

    public String getFeedInfo(String userName, String feedName) {
        String adaFruitFeedInfo = adaFruitProxy.getAdaFruitFeedInfo(userName, feedName, AIOKey);
        return adaFruitFeedInfo;
    }

    public String getFeedData(String userName, String feedName) {
        String adaFruitFeedData = adaFruitProxy.getAdaFruitFeedData(userName, feedName, AIOKey);
        return adaFruitFeedData;
    }
}
