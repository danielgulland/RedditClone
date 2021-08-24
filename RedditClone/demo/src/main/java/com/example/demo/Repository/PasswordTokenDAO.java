package com.example.demo.Repository;

import com.example.demo.Models.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordTokenDAO extends CrudRepository<PasswordResetToken, Integer> {
//    @Query(value = "SELECT * FROM password_reset_token WHERE user_id = ?1", nativeQuery = true)
    Optional<PasswordResetToken> findByUserId(long userId);
}
