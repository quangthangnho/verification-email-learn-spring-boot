package com.verificationemail.verificationemail.config;

import com.verificationemail.verificationemail.service.impl.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JPAConfig {
     //https://springbootdev.com/2018/03/13/spring-data-jpa-auditing-with-createdby-createddate-lastmodifiedby-and-lastmodifieddate/ config
    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
