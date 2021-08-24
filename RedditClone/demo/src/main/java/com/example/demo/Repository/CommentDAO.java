package com.example.demo.Repository;

import com.example.demo.Models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentDAO extends CrudRepository<Comment, Long> {
}
