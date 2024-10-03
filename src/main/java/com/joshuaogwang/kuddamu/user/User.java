package com.joshuaogwang.kuddamu.user;

import com.joshuaogwang.kuddamu.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = true)
    private String password;
    private Role Role;
}
