package com.joshuaogwang.kuddamu.user;

import jakarta.persistence.Id;

import java.util.Date;

public class BlackListedToken {
    @Id
    private String token;
    private Date date;
}
