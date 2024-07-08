package com.example.rqchallenge.employees.exception;

public class NotFoundException extends ClientException {
    public NotFoundException(String code, String message){
        super(code, message);
    }
}
