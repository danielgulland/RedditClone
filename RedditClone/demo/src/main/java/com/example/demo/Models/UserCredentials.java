package com.example.demo.Models;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class UserCredentials {

    @Getter
    private String username;
    @Getter
    private String password;

    public UserCredentials() {

    }

    public UserCredentials(String username) {
        this.username = username;
    }
}
