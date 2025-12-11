package io.github.wolfandw.controller.advicer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionAdvicer {
    private static final String ERROR = "Error";

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ERROR, e.getMessage()));
    }
}
