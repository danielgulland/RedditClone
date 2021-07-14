package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

public class PasswordReset {
    @Getter
    @Setter
    private String newPassword;

    @Getter
    @Setter
    private String confirmPassword;
}
