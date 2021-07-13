package com.example.demo.Manager;

import com.example.demo.Models.CommonResponse;
import com.example.demo.Models.User;
import com.example.demo.Repository.UserDAO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrationManager {

    public CommonResponse validateUsername(User user, UserDAO userDAO) {
        Optional<User> existingUser = userDAO.findByUsername(user.getUsername());

        if(existingUser.isEmpty()) {
            return new CommonResponse(true, "Username is valid");
        }

        return new CommonResponse(false, "Username already exists");
    }

    public CommonResponse validateEmail(User user, UserDAO userDAO) {
        Optional<User> existingUser = userDAO.findByEmail(user.getEmail());

        if(existingUser.isEmpty()) {
            return new CommonResponse(true, "Email is valid");
        }

        return new CommonResponse(false, "Email already exists");
    }
}