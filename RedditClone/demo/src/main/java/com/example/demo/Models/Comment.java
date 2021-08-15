package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    Date dateCreated;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
}
