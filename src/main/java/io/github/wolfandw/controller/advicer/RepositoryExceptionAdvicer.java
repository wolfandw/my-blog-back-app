package io.github.wolfandw.controller.advicer;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class RepositoryExceptionAdvicer {
    private static final String ERROR = "Error";

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Map<String, String>> handleEmptyResulException(EmptyResultDataAccessException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(ERROR, e.getMessage()));
    }
}
