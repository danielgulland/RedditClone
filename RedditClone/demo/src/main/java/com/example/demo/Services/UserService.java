package com.example.demo.Services;

import com.example.demo.Models.EmailConfirmationToken;
import com.example.demo.Models.PasswordResetToken;
import com.example.demo.Models.User;
import com.example.demo.Repository.EmailConfirmationDAO;
import com.example.demo.Repository.PasswordTokenDAO;
import com.example.demo.Repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.demo.Constants.FieldConstants.TOTAL_MINUTES_UNTIL_TOKEN_EXPIRES;

@Service
public class UserService {
    final String REGISTER_URL = "http://localhost:8080/register";

    final String EMAIL_SENT_FROM = "applicationtestingnatedan@gmail.com";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailConfirmationDAO emailConfirmationDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordTokenDAO passwordTokenDAO;


    public User getUserById(final long id) {
        final Optional<User> user = userDAO.findByUserId(id);

        if (user.isPresent()) {
            return user.get();
        }

        throw new RuntimeException("bad");
    }

    public User getUserByUsername(final String username) {
        final Optional<User> user = userDAO.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new RuntimeException("bad");
//        throw new ApiException("bad", ValidationError.NOT_FOUND);
    }

    public User getUserByEmail(final String email) {
        final Optional<User> user = userDAO.findByEmail(email);

        if(user.isPresent()) {
            return user.get();
        }

        throw new RuntimeException("bad");
    }

    public void registerUser(User user) {
        Optional<User> existingUserWithUsername = userDAO.findByUsername(user.getUsername());
        Optional<User> existingUserWithEmail = userDAO.findByEmail(user.getEmail());

        if(existingUserWithEmail.isPresent() && existingUserWithUsername.isPresent()) {
            throw new RuntimeException("Username and email already exist");
        }
        else if(existingUserWithEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        else if(existingUserWithUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPass);

        userDAO.save(user);

        String token = UUID.randomUUID().toString();
        String contextPath = REGISTER_URL + "/confirmEmail?token=" + token + "&userId=" + user.getUserId();
        emailService.createEmailConfirmationToken(user.getUserId(), token, new Date());
        System.out.println(user.getUserId());
        emailService.constructAndSendEmailVerification(contextPath, user.getEmail(), EMAIL_SENT_FROM);
    }

    public void confirmEmail(String token, long userId) {
        Optional<User> existingUser = userDAO.findByUserId(userId);
        Optional<EmailConfirmationToken> confirmEmailToken = emailConfirmationDAO.findByUserId(userId);

        if(existingUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }

        if (confirmEmailToken.isEmpty()) {
            throw new RuntimeException("Email does not exist");
        }

        if(confirmEmailToken.get().getToken().equals(token)) {
            emailConfirmationDAO.delete(confirmEmailToken.get());
            existingUser.get().setVerifiedEmail(true);
            userDAO.save(existingUser.get());
        }
    }

    public void deleteUser(long userId) {
        Optional<User> existingUser = userDAO.findByUserId(userId);

        existingUser.ifPresent(user -> userDAO.delete(user));
    }

    public void resetPassword(String email) {
        Optional<User> existingUser = userDAO.findByEmail(email);

        if (existingUser.isPresent()) {
            String token = UUID.randomUUID().toString();
            String contextPath = REGISTER_URL + "/checkToken?token=" + token + "&userId=" + existingUser.get().getUserId();
            emailService.createPasswordResetToken(existingUser.get().getUserId(), token, new Date());
            emailService.constructAndSendResetTokenEmail(contextPath, email, EMAIL_SENT_FROM);
        }
        else {
            throw new RuntimeException("Email does not exist");
        }
    }

    public void savePassword(String newPassword, String confirmPassword, long userId) {
        Optional<User> existingUser = userDAO.findByUserId(userId);

        if(existingUser.isPresent()) {
            if(newPassword.equals(confirmPassword)) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String hashedPass = bCryptPasswordEncoder.encode(newPassword);
                existingUser.get().setPassword(hashedPass);

                userDAO.save(existingUser.get());
            }
        }
        else {
            throw new RuntimeException("Passwords don't match.");
        }
    }

    public boolean checkForExpiredToken(Date createdTokenDate) {
        Date currentDate = new Date();

        long difference =  currentDate.getTime() - createdTokenDate.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);

        return minutes <= TOTAL_MINUTES_UNTIL_TOKEN_EXPIRES;
    }

    public boolean checkPasswordResetToken(String token, long userId) {
        Optional<User> existingUser = userDAO.findByUserId(userId);
        Optional<PasswordResetToken> passwordResetToken = passwordTokenDAO.findByUserId(userId);

        if(existingUser.isPresent() && passwordResetToken.isPresent()) {
            passwordTokenDAO.delete(passwordResetToken.get());
            return passwordResetToken.get().getToken().equals(token) && checkForExpiredToken(passwordResetToken.get().getExpiredTokenDate());
        }
        return false;
    }
}