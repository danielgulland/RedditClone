package com.example.demo.ApiControllers;

import com.example.demo.Models.UserCredentials;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.hibernate.Session;


@RestController
@RequestMapping("/loginUser")
public class LoginController {

    @Autowired
    private UserCredentials userCredentials;
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public void loginUser(@RequestBody UserCredentials userCredentials) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String hashedPass = bCryptPasswordEncoder.encode("helloman");

        bCryptPasswordEncoder.matches(userCredentials.getPassword(), hashedPass);

        System.out.println(userCredentials.getUsername());
    }
}
