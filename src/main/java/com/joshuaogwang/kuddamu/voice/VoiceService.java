package com.joshuaogwang.kuddamu.voice;

import com.africastalking.AfricasTalking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VoiceService {
    private final com.africastalking.VoiceService voiceService;

    public VoiceService() {
        this.voiceService = AfricasTalking.getService(String.valueOf(VoiceService.class));
    }

    public void makeCall(String phoneNumber) throws IOException {
        voiceService.call(phoneNumber);
    }

//    public String generateVoiceXml() {
//        return new com.africastalking.voice.ActionBuilder()
//                .say("Welcome to the Disaster Response System.")
//                .getDigits("Press 1 for emergency, 2 for assistance", 1)
//                .build();
//    }
}
