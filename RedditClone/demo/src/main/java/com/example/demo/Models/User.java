package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class User {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column
    @Getter
    @Setter
    @NotNull
    private String username;

    @Column
    @Getter
    @Setter
    @NotNull
    private String password;

    @Column
    @Getter
    @Setter
    @NotNull
    private String email;

    @Column
    @Getter
    @Setter
    private boolean verifiedEmail;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private PasswordResetToken passwordResetToken;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private EmailConfirmationToken emailConfirmationToken;
}
