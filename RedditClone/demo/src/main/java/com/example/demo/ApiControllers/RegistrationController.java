package com.example.demo.ApiControllers;

import com.example.demo.Models.PasswordReset;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;
import com.example.demo.Validation.ValidationError;
import com.example.demo.Validation.Validator;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.Constants.FieldConstants.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity registerUser(@Valid @RequestBody User user) {

        Matcher matcher = pattern.matcher(user.getEmail());
        if(validator.chain(StringUtils.isNullOrEmpty(user.getUsername()), ValidationError.MISSING_VALUE, USERNAME)
                .chain(user.getUsername().length() > 20, ValidationError.USERNAME_EXCEEDS_LENGTH, USERNAME)
                .chain(StringUtils.isNullOrEmpty(user.getPassword()), ValidationError.MISSING_VALUE, PASSWORD)
                .chain(user.getPassword().length() < 8, ValidationError.PASSWORD_DOES_NOT_MEET_REQUIRED_LENGTH, PASSWORD)
                .chain(StringUtils.isNullOrEmpty(user.getEmail()), ValidationError.MISSING_VALUE, EMAIL)
                .checkForNoErrors(!matcher.matches(), ValidationError.INVALID_EMAIL_FORMAT, EMAIL)) {

            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam("userId") long userId) {

        if(validator.checkForNoErrors(userId <= 0, ValidationError.BAD_REQUEST, USER_ID)) {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(HttpServletRequest request, @RequestParam("email") String email) {

        if(validator.checkForNoErrors(StringUtils.isNullOrEmpty(email), ValidationError.MISSING_VALUE, EMAIL)) {
            userService.resetPassword(email);
            System.out.println("RESET PASSWORD");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @GetMapping("/confirmEmail")
    private ResponseEntity confirmEmail(@RequestParam("token") String token, @RequestParam("userId") long userId) {

        if(validator.checkForNoErrors(StringUtils.isNullOrEmpty(token), ValidationError.MISSING_VALUE, EMAIL_RESET_TOKEN)) {
            userService.confirmEmail(token, userId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }

    @GetMapping("/checkPasswordResetToken")
    private ResponseEntity checkPasswordResetToken(@RequestParam("token") String token, @RequestParam("userId") long userId) {

        if(validator.chain(StringUtils.isNullOrEmpty(token), ValidationError.MISSING_VALUE, PASSWORD_RESET_TOKEN)
                .checkForNoErrors(userId <= 0, ValidationError.BAD_REQUEST, USER_ID)) {

                if (userService.checkPasswordResetToken(token, userId)) {
                    return ResponseEntity.status(HttpStatus.OK).body(null);
                }
            }

        return validator.getResponseEntity();
    }

    @PostMapping("/savePassword")
    private ResponseEntity savePassword(@RequestBody PasswordReset passwordReset, long userId) {

        if(validator.chain(StringUtils.isNullOrEmpty(passwordReset.getNewPassword()), ValidationError.MISSING_VALUE, PASSWORD)
                .chain(StringUtils.isNullOrEmpty(passwordReset.getConfirmPassword()), ValidationError.MISSING_VALUE, PASSWORD)
            .checkForNoErrors(userId <= 0, ValidationError.BAD_REQUEST, USER_ID)) {

            userService.savePassword(passwordReset.getNewPassword(), passwordReset.getConfirmPassword(), userId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return validator.getResponseEntity();
    }
}