package com.verificationemail.verificationemail.service.impl;

import com.verificationemail.verificationemail.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("confirm your email");
            mimeMessageHelper.setFrom("quang@gmail.com");
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e) {
            LOGGER.error("failed to send email: {}", e);
        }
    }
}
