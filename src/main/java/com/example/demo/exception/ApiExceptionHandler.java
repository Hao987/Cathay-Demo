package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity<Object> handleException(ApplicationException e) {
        ApiExcepiton apiExcepiton = new ApiExcepiton(e.getMessage(), HttpStatus.OK, ZonedDateTime.now());
        return new ResponseEntity<>(apiExcepiton, HttpStatus.BAD_REQUEST);
    }
}
