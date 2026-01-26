package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        logger.error("MethodArgumentNotValidException is thrown");

        Map<String, String> fields = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fields.put(error.getField(), error.getDefaultMessage());
        }

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                fields
        );
    }

    /* ===================== 404 – Not Found ===================== */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex) {

        logger.error("ResourceNotFoundException is thrown");

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null
        );
    }

    /* ===================== 409 – Illegal State ===================== */

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException ex) {

        logger.error("IllegalStateException is thrown");

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                null
        );
    }

    /* ===================== 409 – Optimistic Locking ===================== */

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex) {

        logger.error("ObjectOptimisticLockingFailureException is thrown");

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Concurrent modification detected. Please retry.",
                null
        );
    }

    /* ===================== 500 – Unexpected ===================== */

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneric(
//            Exception ex) {
//
//        logger.error("Internal Server Error is thrown");
//
//        return buildErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Unexpected server error",
//                null
//        );
//    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> ForbiddenException(
            ForbiddenException ex) {

        logger.error("ForbiddenException is thrown");

        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                null
        );
    }


    private ResponseEntity<Map<String, Object>> buildErrorResponse (
            HttpStatus status,
            String message,
            Map < String, String > fields){

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", Instant.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);

        if (fields != null && !fields.isEmpty()) {
            response.put("fields", fields);
        }

        return ResponseEntity.status(status).body(response);

    }
}
