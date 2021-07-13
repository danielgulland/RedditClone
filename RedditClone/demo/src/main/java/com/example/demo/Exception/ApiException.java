package com.example.demo.Exception;

import com.example.demo.Validation.ValidationError;

import java.util.Collections;
import java.util.List;

public class ApiException extends Exception {

    private ValidationError error;
    private List<String> fields;

    public ApiException(final String message, final ValidationError error) {
        super(message);
        this.error = error;
        this.fields = Collections.emptyList();
    }
}
