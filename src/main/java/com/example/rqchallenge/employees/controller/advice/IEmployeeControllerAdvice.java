package com.example.rqchallenge.employees.controller.advice;

import com.example.rqchallenge.employees.exception.BadRequestException;
import com.example.rqchallenge.employees.exception.NotFoundException;
import com.example.rqchallenge.employees.exception.TooManyRequestsException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IEmployeeControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<String> handleTooManyRequestsException(TooManyRequestsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(429));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(500));
    }
}
