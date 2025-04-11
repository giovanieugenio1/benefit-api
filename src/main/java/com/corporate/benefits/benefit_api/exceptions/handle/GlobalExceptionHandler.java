package com.corporate.benefits.benefit_api.exceptions.handle;

import com.corporate.benefits.benefit_api.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.NOT_FOUND.value());
        values.put("error", "Resource not found!");
        values.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(values);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.BAD_REQUEST.value());
        values.put("error", "Validation error");
        values.put("message", errors);

        return ResponseEntity.badRequest().body(values);
    }

    @ExceptionHandler(CpfValidationException.class)
    public ResponseEntity<Map<String, Object>> handleCpfValidationException(CpfValidationException ex) {
        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.BAD_REQUEST.value());
        values.put("error", "Validation error");
        values.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(values);
    }

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<Map<String, Object>> handleEmailValidationException(EmailValidationException ex) {
        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.BAD_REQUEST.value());
        values.put("error", "Validation error");
        values.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(values);
    }

    @ExceptionHandler(BenefitAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleBenefitNameValidationException(BenefitAlreadyExistsException ex) {
        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.BAD_REQUEST.value());
        values.put("error", "Validation error");
        values.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(values);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserValidationException(UserAlreadyExistsException ex) {
        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.BAD_REQUEST.value());
        values.put("error", "Validation error");
        values.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(values);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidEx) {
            if (invalidEx.getTargetType().isEnum() &&
                    invalidEx.getTargetType().getSimpleName().equals("Role")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of(
                                "error", "Bad Request",
                                "message", "Invalid role. Accepted values: ROLE_ADMIN or ROLE_USER.",
                                "status", 400,
                                "timestamp", LocalDateTime.now()
                        )
                );
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "error", "Bad Request",
                        "message", "Malformed JSON request",
                        "status", 400,
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> values = new HashMap<>();

        values.put("timestamp", LocalDateTime.now());
        values.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        values.put("error", "Internal Server Error");
        values.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(values);
    }
}
