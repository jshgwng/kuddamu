package com.joshuaogwang.kuddamu.emergency;

import com.joshuaogwang.kuddamu.audit.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emergencies")
public class Emergency {
    @Id
    @GeneratedValue
    private Long id;
    private String reportName;
    private String reporterContact;
    private String emergencyType;
    private String location;
    private String emergencyDescription;
    private SeverityLevel severityLevel;
    private Date reportedAt;
    private Status status;
    private String responseAction;
    private Date resolvedAt;
}
