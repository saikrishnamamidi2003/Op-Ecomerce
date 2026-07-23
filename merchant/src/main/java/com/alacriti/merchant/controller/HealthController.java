package com.alacriti.merchant.controller;

import com.alacriti.merchant.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService){
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public String health(){
        return "Merchant service is running successfully";
    }

    @GetMapping("health/db")

    public String databaseHealth(){
        return healthService.getDatabaseVersion();
    }
}
