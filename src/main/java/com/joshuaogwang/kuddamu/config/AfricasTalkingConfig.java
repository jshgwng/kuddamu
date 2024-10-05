package com.joshuaogwang.kuddamu.config;


import com.africastalking.AfricasTalking;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AfricasTalkingConfig {
    @Value("${api.username}")
    private String username;

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void initializeAfricasTalking() {
        AfricasTalking.initialize(username, apiKey);
    }
}
