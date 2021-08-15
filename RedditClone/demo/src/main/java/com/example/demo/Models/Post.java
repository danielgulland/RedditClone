package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    int points;

    @Getter
    @Setter
    String information;

    @Getter
    @Setter
    Date dateCreated;

    @OneToMany
    private List<Comment> comments;

    @Getter
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Getter
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "subredditId", referencedColumnName = "subredditId")
    private Subreddit subreddit;
}
