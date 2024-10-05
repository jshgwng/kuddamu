package com.joshuaogwang.kuddamu.sms;

import com.africastalking.AfricasTalking;
import com.africastalking.sms.Recipient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class SmsService {
    private com.africastalking.SmsService smsService;
    @Value("${api.username}")
    private String username;

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void initializeAfricasTalking() {
        AfricasTalking.initialize(username, apiKey);

        this.smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
    }

    public List<Recipient> sendSms(String message,String[] phoneNumbers) throws IOException {
        return smsService.send(message, phoneNumbers, true);
    }
}
