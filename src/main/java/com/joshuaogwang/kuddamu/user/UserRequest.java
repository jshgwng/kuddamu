package com.joshuaogwang.kuddamu.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
}
