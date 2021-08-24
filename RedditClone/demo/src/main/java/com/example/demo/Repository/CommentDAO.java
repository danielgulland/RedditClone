package com.example.demo.Repository;

import com.example.demo.Models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentDAO extends CrudRepository<Comment, Integer> {
    Optional<Comment> findByCommentId(long commentId);
}
