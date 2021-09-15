package com.verificationemail.verificationemail.service.impl;

import com.verificationemail.verificationemail.dto.ConfirmationTokenDto;
import com.verificationemail.verificationemail.mapper.ConfirmationTokenMapper;
import com.verificationemail.verificationemail.model.ConfirmationToken;
import com.verificationemail.verificationemail.repository.ConfirmationTokenRepository;
import com.verificationemail.verificationemail.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenMapper confirmationTokenMapper;

    public ConfirmationServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, ConfirmationTokenMapper confirmationTokenMapper) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.confirmationTokenMapper = confirmationTokenMapper;
    }


    @Override
    public String saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
        return "save confirmation token work";
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

}
