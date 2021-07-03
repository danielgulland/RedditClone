package com.example.demo.ApiControllers;

import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public void registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        try {
            if(existingUser.isEmpty()) {
                userRepository.save(user);
            }
            else {
                throw new Exception("Username already exists...");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}