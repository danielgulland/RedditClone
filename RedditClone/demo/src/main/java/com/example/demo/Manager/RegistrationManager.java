//package com.example.demo.Manager;
//
//import com.example.demo.Models.CommonResponse;
//import com.example.demo.Models.User;
//import com.example.demo.Repository.UserDAO;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//public class RegistrationManager {
//
//<<<<<<< Updated upstream
//    public CommonResponse validateUsername(User user, UserDAO userDAO) {
//        Optional<User> existingUser = userDAO.findByUsername(user.getUsername());
//=======
//
//
//    public CommonResponse validateUsernameDoesNotExist(User user, UserRepository userRepository) {
//        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
//>>>>>>> Stashed changes
//
//        if(existingUser.isEmpty()) {
//            return new CommonResponse(true, "Username is valid");
//        }
//
//        return new CommonResponse(false, "Username already exists");
//    }
//
//    public CommonResponse validateEmail(User user, UserDAO userDAO) {
//        Optional<User> existingUser = userDAO.findByEmail(user.getEmail());
//
//
//        if(existingUser.isEmpty()) {
//            return new CommonResponse(true, "Email is valid");
//        }
//
//        return new CommonResponse(false, "Email already exists");
//    }
//
//    public CommonResponse validateEmailFormat(User user, UserRepository userRepository) {
////        Matcher matcher = pattern.matcher(user.getEmail());
//
//        return new CommonResponse(true, "Email");
//    }
//}