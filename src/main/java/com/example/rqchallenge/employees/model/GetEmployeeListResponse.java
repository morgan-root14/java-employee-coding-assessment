package com.example.rqchallenge.employees.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeListResponse {
    private String status;
    private List<Employee> data;
    private String message;
}
