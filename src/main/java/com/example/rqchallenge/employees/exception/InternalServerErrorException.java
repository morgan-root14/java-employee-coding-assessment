package com.example.rqchallenge.employees.exception;

public class InternalServerErrorException extends ClientException {
    public InternalServerErrorException(String code, String message){
        super(code, message);
    }
}
