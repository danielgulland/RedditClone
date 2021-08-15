package com.example.demo.Repository;

import com.example.demo.Models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostDAO extends CrudRepository<Post, Integer> {
    Optional<Post> findByPostId(long postId);
}
