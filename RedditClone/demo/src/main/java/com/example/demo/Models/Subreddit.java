package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Subreddit {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subredditId;

    @Getter
    @Setter
    String subredditName;

    @Getter
    @Setter
    String sideSummary;

    @Getter
    @Setter
    Date dateCreated;
}
