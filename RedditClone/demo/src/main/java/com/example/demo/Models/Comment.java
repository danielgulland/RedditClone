package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Getter
    @Setter
    String content;

    @Getter
    @Setter
    String dateCreated;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    public Comment() {}

    public Comment(String content, String dateCreated, Post post) {
        this.content = content;
        this.dateCreated = dateCreated;
        this.post = post;
    }
}
