package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;

import java.util.List;
import java.util.Map;

public interface RqChallengeService {
    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByNameSearch(String searchString);

    Employee getEmployeeById(String id);

    Integer getHighestSalaryOfEmployees();

    List<String> getTopTenHighestEarningEmployeeNames();

    String createEmployee(Map<String, Object> employeeInput);

    String deleteEmployeeById(String id);
}
