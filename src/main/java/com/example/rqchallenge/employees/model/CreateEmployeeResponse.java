package com.example.rqchallenge.employees.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeResponse {
    private String status;
    private Map<String, Object> data;
}
