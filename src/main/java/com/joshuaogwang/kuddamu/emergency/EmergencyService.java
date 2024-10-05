package com.joshuaogwang.kuddamu.emergency;

import com.joshuaogwang.kuddamu.user.Role;
import com.joshuaogwang.kuddamu.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmergencyService {
    private final EmergencyRepository emergencyRepository;


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
            return "CON Thank you. Now, please enter emergency type:";
        }

        private String saveEmergencyType(String emergencyType) {
            this.emergencyType = emergencyType;
            state = "LOCATION";
            return "CON Thank you. Now, please enter location:";
        }

        private String saveLocation(String location) {
            this.location = location;
            state = "SEVERITY_LEVEL";
            return "CON Thank you. Now, please enter severity level:";
        }

        private String saveSeverityLevel(String severityLevel) {
            this.severityLevel = severityLevel;
            state = "COMPLETE";
            SeverityLevel severityLevel1 = SeverityLevel.valueOf(severityLevel.toUpperCase());
            var emergency = Emergency
                    .builder()
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
            return "END Thank you for submitting. We have recorded your emergency";
        }
    }
}
