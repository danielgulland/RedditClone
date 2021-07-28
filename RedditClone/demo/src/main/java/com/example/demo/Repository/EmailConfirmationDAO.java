package com.example.demo.Repository;

import com.example.demo.Models.EmailConfirmationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailConfirmationDAO extends CrudRepository<EmailConfirmationToken, Integer> {
    Optional<EmailConfirmationToken> findByUserId(long userId);
}
