package com.example.demo.Repository;

import com.example.demo.Models.Comment;
import com.example.demo.Models.EmailConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface CommentDAO extends CrudRepository<Comment, Integer> {
}
