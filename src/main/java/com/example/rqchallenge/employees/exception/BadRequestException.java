package com.example.rqchallenge.employees.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends ClientException {
    public BadRequestException(String code, String message){
        super(code, message);
    }
}
