package com.joshuaogwang.kuddamu.user;

import jakarta.persistence.Entity;
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
@Table(name = "blacklisted_tokens")
public class BlackListedToken {
    @Id
    private String token;
    private Date date;
}
