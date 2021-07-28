package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class EmailConfirmationToken {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long emailConfirmationId;

    @Getter
    @Setter
    private long userId;

    @Getter
    @Setter
    String token;

    @Getter
    @Setter
    Date dateCreated;

    public EmailConfirmationToken() {}

    public EmailConfirmationToken(long userId, String token, Date dateCreated) {
        this.userId = userId;
        this.token = token;
        this.dateCreated = dateCreated;
    }
}
