package com.joshuaogwang.kuddamu.sms;

import com.africastalking.sms.Recipient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendSms(@RequestBody SmsRequest smsRequest) throws IOException {
//        try {
            List<Recipient> recipients = smsService.sendSms(smsRequest.getMessage(), smsRequest.getPhoneNumbers());
        System.out.println(smsRequest);
            return ResponseEntity.ok(new SmsResponse("SMS sent successfully", recipients));
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body(new SmsResponse("Failed to send SMS: " + e.getMessage(), null));
//        }
    }
}

@Setter
@Getter
class SmsRequest {
    // Getters and setters
    private String message;
    private String[] phoneNumbers;

}

@Setter
@Getter
class SmsResponse {
    // Getters and setters
    private String message;
    private List<Recipient> recipients;

    public SmsResponse(String message, List<Recipient> recipients) {
        this.message = message;
        this.recipients = recipients;
    }

}