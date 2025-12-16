package io.github.wolfandw.controller.advicer;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

/**
 * Обработчик исключений контроллеров.
 */
@RestControllerAdvice
public class PostControllerAdvicer {
    private static final String ERROR = "Error";

    /**
     * Обрабатывает исключение MethodArgumentTypeMismatchException.
     *
     * @param e исключение типа MethodArgumentTypeMismatchException
     * @return ответ с описанием ошибки
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ERROR, e.getMessage()));
    }

    /**
     * Обрабатывает исключение DataAccessException.
     *
     * @param e исключение типа DataAccessException
     * @return ответ с описанием ошибки
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleException(DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(ERROR, e.getMessage()));
    }

    /**
     * Обрабатывает непредвиденные исклчючения.
     *
     * @param e исключение типа Exception
     * @return ответ с описанием ошибки
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(ERROR, e.getMessage()));
    }
}
