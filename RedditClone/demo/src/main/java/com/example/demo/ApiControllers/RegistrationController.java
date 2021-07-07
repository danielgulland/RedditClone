package com.example.demo.ApiControllers;

import com.example.demo.Manager.RegistrationManager;
import com.example.demo.Models.CommonResponse;
import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RegistrationManager registrationManager;

    @PostMapping("/user")
    public String registerUser(@Valid @RequestBody User user) {
        CommonResponse usernameResponse = registrationManager.validateUsername(user, userRepository);
        CommonResponse emailResponse = registrationManager.validateEmail(user, userRepository);

        if(!usernameResponse.isResponse()) {
            return usernameResponse.getMessage();
        }

        if(!emailResponse.isResponse()) {
            return emailResponse.getMessage();
        }

        userRepository.save(user);
        return usernameResponse.getMessage() + " " + emailResponse.getMessage();
    }
}