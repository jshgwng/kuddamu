package com.joshuaogwang.kuddamu.voice;

import com.africastalking.AfricasTalking;
import com.africastalking.VoiceService;
import com.africastalking.voice.CallResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoiceCallService {
    private com.africastalking.VoiceService voiceService;

    @Value("${api.username}")
    private String username;

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void initializeAfricasTalking() {
        AfricasTalking.initialize(username, apiKey);

        this.voiceService = AfricasTalking.getService(AfricasTalking.SERVICE_VOICE);
    }


    public CallResponse makeCall(String phoneNumber) throws IOException {
        voiceService.call(phoneNumber,"+256323200999");
        return null;
    }

//    public String generateVoiceXml() {
//        return new com.africastalking.voice.ActionBuilder()
//                .say("Welcome to the Disaster Response System.")
//                .getDigits("Press 1 for emergency, 2 for assistance", 1)
//                .build();
//    }
}
