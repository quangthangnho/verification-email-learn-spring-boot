package com.verificationemail.verificationemail.service;

import com.verificationemail.verificationemail.dto.RegistrationRequest;

public interface RegistrationService {
    public String register(RegistrationRequest request);

    public String confirmToken(String token);
}
