package com.joshuaogwang.kuddamu.ussd;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UssdController {
    private final UssdService ussdService;

    public UssdController(UssdService ussdService) {
        this.ussdService = ussdService;
    }

    @PostMapping("/ussd")
    public String handleUssdRequest(@RequestParam String sessionId,
                                    @RequestParam String serviceCode,
                                    @RequestParam String phoneNumber,
                                    @RequestParam String text) {
        return ussdService.handleUssdRequest(sessionId, serviceCode, phoneNumber, text);
    }
}
