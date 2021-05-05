package com.example.foodkitchen.api.controllers.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<?> error(Throwable e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
