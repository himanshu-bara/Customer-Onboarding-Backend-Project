package com.oracle.customer.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Validation Failed");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage()));
        error.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 🔹 Feign Client Error
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignError(FeignException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", ex.status());
        error.put("error", "Feign Client Error");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.status()));
    }

    // 🔹 KYC Not Found
    @ExceptionHandler(com.oracle.customer.exception.KycNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleKycNotFound(com.oracle.customer.exception.KycNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "KYC Not Found");
    }

    // 🔹 KYC Already Exists
    @ExceptionHandler(com.oracle.customer.exception.KycAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleKycExists(com.oracle.customer.exception.KycAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "KYC Already Exists");
    }

    // 🔹 KYC Not Verified
    @ExceptionHandler(com.oracle.customer.exception.KycNotVerifiedException.class)
    public ResponseEntity<Map<String, Object>> handleKycNotVerified(com.oracle.customer.exception.KycNotVerifiedException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "KYC Not Verified");
    }

    // 🔹 Customer Not Found
    @ExceptionHandler(com.oracle.customer.exception.CustomerNotFoundException.class)
    
    public ResponseEntity<Map<String, Object>> handleCustomerNotFound(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "Customer Not Found");
    }

    // 🔹 Account Not Found
    @ExceptionHandler(com.oracle.customer.exception.AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFound(com.oracle.customer.exception.AccountNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "Account Not Found");
    }

    // 🔹 Identity Mismatch (Used in both services)
    @ExceptionHandler(com.oracle.customer.exception.IdentityMismatchException.class)
    
    public ResponseEntity<Map<String, Object>> handleIdentityMismatch(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "Identity Mismatch");
    }

    // 🔹 Generic Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    // 🔧 Utility Method
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, String errorType) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", errorType);
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
}
