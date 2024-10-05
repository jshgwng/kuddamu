package com.joshuaogwang.kuddamu.assistance;

import com.joshuaogwang.kuddamu.emergency.Status;
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
@Table(name = "assistance_requests")
public class Assistance {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String contactNumber;
    private String location;
    private String assistanceType;
    private String description;
    private Date requestDate;
    private Status status;
    private String responseNotes;
    private Date resolvedAt;
}
