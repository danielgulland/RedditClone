package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.ReadableInstant;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long passwordResetId;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "userId")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Getter
    @Setter
    private Date expiredTokenDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public PasswordResetToken(String token, User user, Date expiryDate) {
        this.token = token;
        this.user = user;
        this.expiredTokenDate = expiryDate;
    }
}
