package com.ticket.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ticket.model.CustomError;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomError.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomError ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatusCode());
        body.put("message", ex.getMessage());
        body.put("originClass", ex.getOriginClass());
        body.put("details", ex.getDetails());
        body.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }
}
