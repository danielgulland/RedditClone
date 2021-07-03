package com.example.demo.Validation;

import com.example.demo.Models.User;

import java.util.Optional;

public class Manager {

    public void ValidateUsername(Optional<User> user) {
        try {
            if(user.isPresent()) {
                throw new Exception("Username already exists...");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
