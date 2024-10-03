package com.joshuaogwang.kuddamu.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware",dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {
    @Bean
    AuditorAware<String> auditorAware(){
        return new AuditAwareImpl();
    }

    @Bean
    public DateTimeProvider dateTimeProvider(){
        return ()-> Optional.of(LocalDateTime.now());
    }
}
