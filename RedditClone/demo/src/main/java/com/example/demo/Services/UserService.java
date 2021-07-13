package com.example.demo.Services;

import com.example.demo.Exception.ApiException;
import com.example.demo.Models.User;
import com.example.demo.Repository.UserDAO;
import com.example.demo.Validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUserById(final long id) {
        final Optional<User> user = userDAO.findByUserId(id);

        if (user.isPresent()) {
            return user.get();
        }

        throw new RuntimeException("bad");
    }

    public User getUserByUsername(final String username) {
        final Optional<User> user = userDAO.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new RuntimeException("bad");
//        throw new ApiException("bad", ValidationError.NOT_FOUND);
    }
}
