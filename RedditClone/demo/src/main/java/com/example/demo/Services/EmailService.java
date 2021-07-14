package com.example.demo.Services;

import com.example.demo.Models.PasswordResetToken;
import com.example.demo.Models.User;
import com.example.demo.Repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmailService {

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void createPasswordResetToken(User user, String token, Date experationDate) {
        PasswordResetToken myToken = new PasswordResetToken(token, user, experationDate) ;
        passwordTokenRepository.save(myToken);
    }

    public void constructAndSendResetTokenEmail(String contextPath, String email, final String EMAIL_SENT_FROM) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_SENT_FROM);
            message.setTo(email);
            message.setSubject("Password Reset");
            message.setText(contextPath);
            emailSender.send(message);
            System.out.println("Sent..");
    }
}
