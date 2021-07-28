package com.example.demo.Validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ValidationError {
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    MISSING_VALUE("Missing value", HttpStatus.BAD_REQUEST),
    DUPLICATE_VALUE("Duplicate value", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT("Invalid format for email", HttpStatus.BAD_REQUEST),
    USERNAME_EXCEEDS_LENGTH("Username exceeds allotted length", HttpStatus.BAD_REQUEST),
    PASSWORD_DOES_NOT_MEET_REQUIRED_LENGTH("Password does not meet required length", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String tag;
    private final HttpStatus status;
}
