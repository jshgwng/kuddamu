package com.joshuaogwang.kuddamu.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken,String> {
    boolean existsByToken(String jwt);
}
