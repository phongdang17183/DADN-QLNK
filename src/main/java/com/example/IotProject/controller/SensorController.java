package com.example.IotProject.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices/sensors")
public class SensorController {
    @PreAuthorize("hasRole('ROLE_User')")
    @GetMapping("/feed_data")
    public String feedData() {
        return "Data feeded successfully!";
    }
}
