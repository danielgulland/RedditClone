package com.example.demo.Validation;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Validator {
    private final Map<String, List<String>> errors;
    private HttpStatus status = HttpStatus.OK;

    public boolean checkForNoErrors(final boolean expression, final @NonNull ValidationError error, final String field) {
        if(expression) {
            addError(error, field);
        }
        return errors.isEmpty();
    }

    public Validator chain(final boolean expression, final @NonNull ValidationError error, final String field) {
        if(expression) {
            addError(error, field);
        }
        return this;
    }

    public ResponseEntity getResponseEntity() {
        return ResponseEntity.status(status).body(errors.isEmpty() ? null : ImmutableMap.of("errors", errors));
    }

    private void addError(final ValidationError error, final String field) {
        List<String> fieldList;
        if(errors.containsKey(error.getTag())) {
            fieldList = errors.get(error.getTag());
        }
        else {
            fieldList = new ArrayList<>();
        }

        if(field != null) {
            fieldList.add(field);
        }

        errors.put(error.getTag(), fieldList);
        status = error.getStatus();
    }
}
