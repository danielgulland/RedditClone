package com.example.demo.Repository;

import com.example.demo.Models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostDAO extends CrudRepository<Post, Long> {
}
