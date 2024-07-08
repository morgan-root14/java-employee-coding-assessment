package com.example.rqchallenge.employees.client;


import com.example.rqchallenge.employees.config.FeignConfig;
import com.example.rqchallenge.employees.model.CreateEmployeeResponse;
import com.example.rqchallenge.employees.model.DeleteEmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.GetEmployeeListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "rqChallengeFeignClient", url = "${client.config.employeeClient.url}", configuration = {FeignConfig.class})
public interface RqChallengeFeignClient {

    @GetMapping("/employees")
    ResponseEntity<GetEmployeeListResponse> getAllEmployees();

    @GetMapping("/employee/{id}")
    ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String id);

    @PostMapping("/create")
    ResponseEntity<CreateEmployeeResponse> createEmployee(Map<String, Object> employeeInput);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<DeleteEmployeeResponse> deleteEmployeeById(@PathVariable String id);
}