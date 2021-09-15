package com.verificationemail.verificationemail.service.impl;

import com.verificationemail.verificationemail.constant.AppUserRole;
import com.verificationemail.verificationemail.dto.RegistrationRequest;
import com.verificationemail.verificationemail.model.AppUserEntity;
import com.verificationemail.verificationemail.model.ConfirmationToken;
import com.verificationemail.verificationemail.repository.ConfirmationTokenRepository;
import com.verificationemail.verificationemail.service.ConfirmationTokenService;
import com.verificationemail.verificationemail.service.EmailSenderService;
import com.verificationemail.verificationemail.service.RegistrationService;
import com.verificationemail.verificationemail.utils.TemplateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.verificationemail.verificationemail.utils.TemplateEmail;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private  EmalValidatorImpl emalValidator;
    @Autowired
    private  AppUserServiceImpl appUserService;
    @Autowired
    private  ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emalValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        String token = appUserService.signUpUser(new AppUserEntity(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));
        TemplateEmail templateEmail = new TemplateEmail();
        String link = "http://localhost:8080/api/v1/registration/confirm?token="+token;
        emailSenderService.send(
                request.getEmail(),
                templateEmail.buildEmail(request.getFirstName(), link));
        return token;
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                                                                         .orElseThrow(() -> new IllegalStateException("token not found!"));
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmAt(token);
        int a  = appUserService.enableAppUser(confirmationToken.getAppUserEntity().getEmail());
        return "confirmed";
    }
}
