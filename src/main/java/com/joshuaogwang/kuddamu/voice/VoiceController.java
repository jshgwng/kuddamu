package com.joshuaogwang.kuddamu.voice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.africastalking.Callback;
import com.africastalking.AfricasTalking;
import com.africastalking.VoiceService;
import com.africastalking.voice.CallResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/voice")
public class VoiceController {
    private final VoiceCallService voiceService;

    public VoiceController(VoiceCallService voiceService) {
        this.voiceService = voiceService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendSms(@RequestParam("number") String number) throws IOException {
//        String callerId = "+256773775942";

        //        try {
            CallResponse response = voiceService.makeCall("+"+number);
//            System.out.println(response.toString());
            return ResponseEntity.ok(response);
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
