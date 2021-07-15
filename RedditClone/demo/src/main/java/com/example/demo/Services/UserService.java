package com.example.demo.Services;

import com.example.demo.Models.PasswordResetToken;
import com.example.demo.Models.User;
import com.example.demo.Repository.PasswordTokenRepository;
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
    private EmailService emailService;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;


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
    }

    public void resetPassword(String email) {
        Optional<User> existingUser = userDAO.findByEmail(email);

        if (existingUser.isPresent()) {
            String token = UUID.randomUUID().toString();
            String contextPath = REGISTER_URL + "/checkToken?token=" + token + "&userId=" + existingUser.get().getUserId();
            emailService.createPasswordResetToken(existingUser.get(), token, new Date());
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

        System.out.println(minutes);

        return minutes <= TOTAL_MINUTES_UNTIL_TOKEN_EXPIRES;
    }

    public boolean checkToken(String token, long userId) {
        Optional<User> existingUser = userDAO.findByUserId(userId);
        Optional<PasswordResetToken> passwordResetToken = passwordTokenRepository.findByUser_userId(userId);

        if(existingUser.isPresent() && passwordResetToken.isPresent()) {
            passwordTokenRepository.delete(passwordResetToken.get());
            return passwordResetToken.get().getToken().equals(token) && checkForExpiredToken(passwordResetToken.get().getExpiredTokenDate());
        }
        return false;
    }
}