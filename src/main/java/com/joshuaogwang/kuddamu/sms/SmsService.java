package com.joshuaogwang.kuddamu.sms;

import com.africastalking.AfricasTalking;
import com.africastalking.sms.Recipient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SmsService {
    private final com.africastalking.SmsService smsService;

    public SmsService() {
        this.smsService = AfricasTalking.getService(String.valueOf(SmsService.class));
    }

    public List<Recipient> sendSms(String message, String[] phoneNumbers) throws IOException {
        return smsService.send(message, phoneNumbers, true);
    }
}
