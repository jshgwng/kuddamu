package com.joshuaogwang.kuddamu.ussd;

import com.africastalking.AfricasTalking;
import com.joshuaogwang.kuddamu.assistance.Assistance;
import com.joshuaogwang.kuddamu.assistance.AssistanceRepository;
import com.joshuaogwang.kuddamu.emergency.*;
import com.joshuaogwang.kuddamu.user.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UssdService {
    private final UserRepository userRepository;
    private final EmergencyRepository emergencyRepository;
    private final AssistanceRepository assistanceRepository;



    private final Map<String, UssdSession> sessions = new HashMap<>();
    private final Map<String, UssdEmergencySession> ussdEmergencySessionMap = new HashMap<>();
    private final Map<String, UssdAssistanceSession> ussdAssistanceSessionHashMap = new HashMap<>();

    public UssdService(UserRepository userRepository, EmergencyService emergencyService, EmergencyRepository emergencyRepository, AssistanceRepository assistanceRepository) {
        this.userRepository = userRepository;
        this.emergencyRepository = emergencyRepository;
        this.assistanceRepository = assistanceRepository;
        com.africastalking.UssdService ussdService = AfricasTalking.getService(String.valueOf(UssdService.class));
    }

    public String handleUssdRequest(String sessionId, String serviceCode, String phoneNumber, String text) {
        if (text.isEmpty()) {
            return "CON Welcome to Disaster Response\n1. Report Emergency\n2. Request Assistance\n3. Receive Alerts\n4. Register";
        } else if (text.startsWith("1")) {
            UssdEmergencySession session = ussdEmergencySessionMap.computeIfAbsent(sessionId, k -> new UssdEmergencySession());
            return session.handleRequest(text);
//            return "CON Select emergency type:\n1. Fire\n2. Flood\n3. Medical";
        } /*else if (text.startsWith("1*")) {
            return "END Thank you for reporting. Help is on the way.";
        }*/ else if (text.startsWith("2")) {
            UssdAssistanceSession session = ussdAssistanceSessionHashMap.computeIfAbsent(sessionId, k -> new UssdAssistanceSession());
            return session.handleRequest(text);
//            return "CON Select assistance type:\n1. Food\n2. Water\n3. Medical";
        } /*else if (text.startsWith("2*")) {
            return "END Your assistance request has been recorded.";
        } else if (text.equals("3")) {
            return "END You will receive alerts for your area.";
        } */ else if (text.startsWith("4")) {
            UssdSession session = sessions.computeIfAbsent(sessionId, k -> new UssdSession());
            return session.handleRequest(text);
        } else {
            return "END Invalid option selected.";
        }
    }

    class UssdSession {
        private String state = "INITIAL";
        private String fullname = "";
        private String location = "";
        private String phoneNumber = "";

        public String handleRequest(String text) {
            String[] textArray = text.split("\\*");
            String input = textArray.length > 1 ? textArray[textArray.length - 1] : "";

            switch (state) {
                case "INITIAL":
                    return promptFullname();
                case "FULLNAME_ENTRY":
                    return saveFullname(input);
                case "LOCATION_ENTRY":
                    return saveLocation(input);
                case "PHONE_ENTRY":
                    return savePhoneNumber(input);
                case "COMPLETE":
                    return displaySummary();
                default:
                    return "END Invalid state";
            }
        }

        private String promptFullname() {
            state = "FULLNAME_ENTRY";
            return "CON Please enter your full name:";
        }

        private String saveFullname(String fullname) {
            this.fullname = fullname;
            state = "LOCATION_ENTRY";
            return "CON Thank you. Now, please enter your location:";
        }

        private String saveLocation(String fullname) {
            this.fullname = fullname;
            state = "PHONE_ENTRY";
            return "CON Thank you. Now, please enter your phone number:";
        }

        private String savePhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            state = "COMPLETE";
            var user = User.builder().fullName(fullname).phoneNumber(phoneNumber).location(location).role(Role.DAP).build();
            userRepository.save(user);
            return displaySummary();
        }

        private String displaySummary() {
            return "END Thank you for registering. We have recorded:\nName: " + fullname + "\nPhone: " + phoneNumber;
        }
    }

    class UssdEmergencySession {
        private String state = "INITIAL";
        private String reportName = "";
        private String reportContact = "";
        private String emergencyType = "";
        private String severityLevel = "";
        private String location = "";

        public String handleRequest(String text) {
            String[] textArray = text.split("\\*");
            String input = textArray.length > 1 ? textArray[textArray.length - 1] : "";

            return switch (state) {
                case "INITIAL" -> promptReporterName();
                case "REPORT_NAME" -> saveReportName(input);
                case "REPORT_CONTACT" -> saveReportContact(input);
                case "EMERGENCY_TYPE" -> saveEmergencyType(input);
                case "LOCATION" -> saveLocation(input);
                case "SEVERITY_LEVEL" -> saveSeverityLevel(input);
                case "COMPLETE" -> displaySummary();
                default -> "END Invalid state";
            };
        }

        private String promptReporterName() {
            state = "REPORT_NAME";
            return "CON Please enter your full name:";
        }

        private String saveReportName(String name) {
            this.reportName = name;
            state = "REPORT_CONTACT";
            return "CON Thank you. Now, please enter your phone number:";
        }

        private String saveReportContact(String phoneNumber) {
            this.reportContact = phoneNumber;
            state = "EMERGENCY_TYPE";
            return "CON Thank you. Now, please select emergency type:\n1. Fire\n2. Flood\n3. Medical";
        }

        private String saveEmergencyType(String emergencyType) {
            this.emergencyType = switch (emergencyType) {
                case "1" -> "Fire";
                case "2" -> "Flood";
                case "3" -> "Medical";
                default -> emergencyType;
            };
            state = "LOCATION";
            return "CON Thank you. Now, please enter location:";
        }

        private String saveLocation(String location) {
            this.location = location;
            state = "SEVERITY_LEVEL";
            return "CON Thank you. Now, please select severity level:\n1. Low\n2. Medium\n3. High";
        }

        private String saveSeverityLevel(String severityLevel) {
            this.severityLevel = switch (severityLevel) {
                case "1" -> "LOW";
                case "2" -> "MEDIUM";
                case "3" -> "HIGH";
                default -> severityLevel.toUpperCase();
            };
            state = "COMPLETE";
            SeverityLevel severityLevel1 = SeverityLevel.valueOf(this.severityLevel);
            Emergency emergency = Emergency.builder()
                    .reportName(reportName)
                    .reporterContact(reportContact)
                    .emergencyType(emergencyType)
                    .location(location)
                    .severityLevel(severityLevel1)
                    .status(Status.PENDING)
                    .reportedAt(new Date())
                    .emergencyDescription("")
                    .build();
            emergencyRepository.save(emergency);
            return displaySummary();
        }

        private String displaySummary() {
            return "END Thank you for submitting. We have recorded your emergency:\n" +
                    "Type: " + emergencyType + "\n" +
                    "Location: " + location + "\n" +
                    "Severity: " + severityLevel;
        }
    }

    public class UssdAssistanceSession {
        private String state = "INITIAL";
        private String name = "";
        private String contactNumber = "";
        private String location = "";
        private String assistanceType = "";

        public String handleRequest(String text) {
            String[] textArray = text.split("\\*");
            String input = textArray.length > 1 ? textArray[textArray.length - 1] : "";

            return switch (state) {
                case "INITIAL" -> promptName();
                case "NAME_ENTRY" -> saveName(input);
                case "CONTACT_ENTRY" -> saveContactNumber(input);
                case "LOCATION_ENTRY" -> saveLocation(input);
                case "ASSISTANCE_TYPE_ENTRY" -> saveAssistanceType(input);
                case "COMPLETE" -> displaySummary();
                default -> "END Invalid state";
            };
        }

        private String promptName() {
            state = "NAME_ENTRY";
            return "CON Please enter your full name:";
        }

        private String saveName(String name) {
            this.name = name;
            state = "CONTACT_ENTRY";
            return "CON Thank you. Now, please enter your contact number:";
        }

        private String saveContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            state = "LOCATION_ENTRY";
            return "CON Thank you. Now, please enter your location:";
        }

        private String saveLocation(String location) {
            this.location = location;
            state = "ASSISTANCE_TYPE_ENTRY";
            return "CON Thank you. Now, please select the type of assistance needed:\n1. Food\n2. Water\n3. Medical\n4. Shelter\n5. Other";
        }

        private String saveAssistanceType(String input) {
            this.assistanceType = switch (input) {
                case "1" -> "Food";
                case "2" -> "Water";
                case "3" -> "Medical";
                case "4" -> "Shelter";
                case "5" -> "Other";
                default -> input;
            };
            state = "COMPLETE";
            saveAssistanceRequest();
            return displaySummary();
        }

        private void saveAssistanceRequest() {
            Assistance assistance = new Assistance();
            assistance.setName(name);
            assistance.setContactNumber(contactNumber);
            assistance.setLocation(location);
            assistance.setAssistanceType(assistanceType);
            assistanceRepository.save(assistance);
        }

        private String displaySummary() {
            return "END Thank you for submitting your assistance request. We have recorded:\n" +
                    "Name: " + name + "\n" +
                    "Contact: " + contactNumber + "\n" +
                    "Location: " + location + "\n" +
                    "Assistance Type: " + assistanceType + "\n" +
                    "We will process your request as soon as possible.";
        }
    }
}
