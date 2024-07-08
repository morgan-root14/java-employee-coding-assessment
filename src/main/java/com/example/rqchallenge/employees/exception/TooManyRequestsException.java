package com.example.rqchallenge.employees.exception;

public class TooManyRequestsException extends ClientException {
    public TooManyRequestsException(String code, String message){
        super(code, message);
    }
}
