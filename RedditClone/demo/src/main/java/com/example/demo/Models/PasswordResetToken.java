package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long passwordResetId;

    @Getter
    @Setter
    private String token;

   @Getter
   @Setter
   private long userId;

    @Getter
    @Setter
    private Date expiredTokenDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, long userId, Date expiryDate) {
        this.token = token;
        this.userId = userId;
        this.expiredTokenDate = expiryDate;
    }
}
