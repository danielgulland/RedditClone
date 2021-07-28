package com.example.demo.Services;

import com.example.demo.Models.EmailConfirmationToken;
import com.example.demo.Models.PasswordResetToken;
import com.example.demo.Repository.EmailConfirmationDAO;
import com.example.demo.Repository.PasswordTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmailService {

    @Autowired
    PasswordTokenDAO passwordTokenDAO;

    @Autowired
    EmailConfirmationDAO emailConfirmationDAO;

    @Autowired
    private JavaMailSender emailSender;

    public void createPasswordResetToken(long userId, String token, Date dateCreated) {
        PasswordResetToken myToken = new PasswordResetToken(token, userId, dateCreated) ;
        passwordTokenDAO.save(myToken);
    }

    public void createEmailConfirmationToken(long userId, String token, Date dateCreated) {
        EmailConfirmationToken myToken = new EmailConfirmationToken(userId, token, dateCreated);
        emailConfirmationDAO.save(myToken);
    }

    public void constructAndSendResetTokenEmail(String contextPath, String email, final String EMAIL_SENT_FROM) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_SENT_FROM);
            message.setTo(email);
            message.setSubject("Password Reset");
            message.setText(contextPath);
            emailSender.send(message);
    }

    public void constructAndSendEmailVerification(String contextPath, String email, final String EMAIL_SENT_FROM) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_SENT_FROM);
            message.setTo(email);
            message.setSubject("Email confirmation");
            message.setText(contextPath);
            emailSender.send(message);
    }
}
