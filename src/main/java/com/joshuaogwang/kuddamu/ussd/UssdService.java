package com.joshuaogwang.kuddamu.ussd;

import com.africastalking.AfricasTalking;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UssdService {
    private final com.africastalking.UssdService ussdService;

    public UssdService() {
        this.ussdService = AfricasTalking.getService(String.valueOf(UssdService.class));
    }

    public String handleUssdRequest(String sessionId, String serviceCode, String phoneNumber, String text) {
        if (text.isEmpty()) {
            return "CON Welcome to Disaster Response\n1. Report Emergency\n2. Request Assistance\n3. Receive Alerts";
        } else if (text.equals("1")) {
            return "CON Select emergency type:\n1. Fire\n2. Flood\n3. Medical";
        } else if (text.startsWith("1*")) {
            return "END Thank you for reporting. Help is on the way.";
        } else if (text.equals("2")) {
            return "CON Select assistance type:\n1. Food\n2. Water\n3. Medical";
        } else if (text.startsWith("2*")) {
            return "END Your assistance request has been recorded.";
        } else if (text.equals("3")) {
            return "END You will receive alerts for your area.";
        } else {
            return "END Invalid option selected.";
        }
    }
}
