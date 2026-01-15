package com.SpringAOP.SmartLockSecuritySystem.Exception;

import com.SpringAOP.SmartLockSecuritySystem.Utilities.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse> UnauthorizedAccessExceptionHandler(UnauthorizedAccessException ex) {
        logger.error("Error Occurred : UnauthorizedAccessException");
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, "False");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HardwareFailureException.class)
    public ResponseEntity<ApiResponse> HardwareFailureExceptionHandler(HardwareFailureException ex) {
        logger.error("Error Occurred : HardwareFailureException");
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, "False");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
