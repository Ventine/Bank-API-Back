package com.example.bank.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Value("${spring.application.name:bank-api}")
    private String applicationName;

    @Value("${server.port:8080}")
    private String port;
 
    @GetMapping("/healthAPI")
    public Map<String, Object> health() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "UP");
        response.put("application", applicationName);
        response.put("port", port);
        response.put("timestamp", Instant.now().toString());
        response.put("javaVersion", System.getProperty("java.version"));
        response.put("jvm", System.getProperty("java.vm.name"));
        response.put("os", System.getProperty("os.name"));
        response.put("thread", Thread.currentThread().getName());

        return response;
    }
}