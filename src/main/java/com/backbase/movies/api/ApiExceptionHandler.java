package com.backbase.movies.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientException(final HttpClientErrorException ex) {
        final Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getStatusText());
        return status(ex.getStatusCode()).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(final Exception ex) {
        return status(INTERNAL_SERVER_ERROR).body(extractMessage(ex));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(final AuthenticationException ex) {
        return status(UNAUTHORIZED).body(extractMessage(ex));
    }

    private Map<String, String> extractMessage(Exception exception) {
        final Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ofNullable(exception.getCause()).map(Throwable::getMessage).orElse(exception.getMessage()));
        return errors;
    }
}
