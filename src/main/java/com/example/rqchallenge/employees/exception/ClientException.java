package com.example.rqchallenge.employees.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ClientException extends RuntimeException{
    private final String code;
    private final String message;
}
