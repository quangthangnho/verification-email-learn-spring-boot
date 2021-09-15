package com.verificationemail.verificationemail.service.impl;

import com.verificationemail.verificationemail.dto.ConfirmationTokenDto;
import com.verificationemail.verificationemail.model.AppUserEntity;
import com.verificationemail.verificationemail.model.ConfirmationToken;
import com.verificationemail.verificationemail.repository.AppUserRepository;
import com.verificationemail.verificationemail.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,  email)));
    }

    public String signUpUser(AppUserEntity appUser){
        AppUserEntity userExists =  appUserRepository.findByEmail(appUser.getEmail()).orElse(null);
        if(Objects.isNull(userExists)) {
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);
            appUserRepository.save(appUser);
            String token = UUID.randomUUID().toString();
            // TODO : send confirmation token
            ConfirmationToken confirmationToken = new ConfirmationToken(token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    appUser
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            return token;
        }
        String token = UUID.randomUUID().toString();
        // TODO : send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userExists
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO  Send email
        return token;
    }
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
