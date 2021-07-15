package com.example.demo.ApiControllers;

import com.example.demo.Models.User;
import com.example.demo.Models.UserCredentials;
import com.example.demo.Repository.UserDAO;
import com.example.demo.Services.UserService;
import com.example.demo.Validation.ValidationError;
import com.example.demo.Validation.Validator;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.demo.Constants.FieldConstants.*;


@RestController
@RequestMapping("/loginUser")
public class LoginController {

    @Autowired
    private UserCredentials userCredentials;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity loginUser(@RequestBody UserCredentials userCredentials) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(validator.chain(StringUtils.isNullOrEmpty(userCredentials.getUsername()), ValidationError.MISSING_VALUE, USERNAME)
                .checkForNoErrors(StringUtils.isNullOrEmpty(userCredentials.getPassword()), ValidationError.MISSING_VALUE, PASSWORD)) {

            User user = userService.getUserByUsername(userCredentials.getUsername());

            if(validator.checkForNoErrors(bCryptPasswordEncoder.matches(userCredentials.getPassword(), user.getPassword()), ValidationError.INVALID_USERNAME_OR_PASSWORD, USERNAME_OR_PASSWORD)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }
        return validator.getResponseEntity();
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        User user = userService.getUserById(1);
        System.out.println("hey" + user.getUserId());
        return validator.getResponseEntity();
    }
}
