package com.EventSphere.BackendAssignment.Exceptions;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

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

    @ExceptionHandler(AllTicketsBookedException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            AllTicketsBookedException ex) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                null
        );
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null
        );
    }



    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException ex) {

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

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Concurrent modification detected. Please retry.",
                null
        );
    }



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneric(
//            Exception ex) {
//
//        return buildErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Unexpected server error",
//                null
//        );
//    }



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
