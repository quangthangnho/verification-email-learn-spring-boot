package com.verificationemail.verificationemail.service.impl;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorAwareImpl implements AuditorAware<String> {

    // config @creadBy @LastModifiedBy
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return Optional.ofNullable(authentication.getName());
        }catch (Exception e) {
            return Optional.of("");
        }

    }
}
