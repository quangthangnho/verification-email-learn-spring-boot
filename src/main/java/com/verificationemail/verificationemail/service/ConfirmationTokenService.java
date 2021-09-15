package com.verificationemail.verificationemail.service;

import com.verificationemail.verificationemail.model.ConfirmationToken;

import java.util.Optional;


public interface ConfirmationTokenService {

    String saveConfirmationToken(ConfirmationToken confirmationToke);

    public Optional<ConfirmationToken> getToken(String token);

    public int setConfirmAt(String token);
}
