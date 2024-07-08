package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.client.RqChallengeFeignClient;
import com.example.rqchallenge.employees.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RqChallengeServiceImpl implements RqChallengeService {

    private final RqChallengeFeignClient rqChallengeFeignClient;

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Retrieving list of all employees");
        return Objects.requireNonNull(rqChallengeFeignClient.getAllEmployees().getBody(), "Employees cannot be null").getData();
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        log.info("Retrieving employee by name ::: {}", searchString);
        return Objects.requireNonNull(rqChallengeFeignClient.getAllEmployees().getBody(), "Employees cannot be null").getData().stream()
                .filter(employee -> employee.getEmployee_name().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(String id) {
        log.info("Retrieving employee by id ::: {}", id);
        return Objects.requireNonNull(rqChallengeFeignClient.getEmployeeById(id).getBody(), "Employee data with id: " + id + "cannot be null").getData();
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        log.info("Retrieving highest employee salary");
        return Objects.requireNonNull(rqChallengeFeignClient.getAllEmployees().getBody(), "Employees cannot be null").getData().stream()
                .max(Comparator.comparing(Employee::getEmployee_salary)).get().getEmployee_salary();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        log.info("Retrieving top ten highest earning employees ny names");
        return Objects.requireNonNull(rqChallengeFeignClient.getAllEmployees().getBody(), "Employees cannot be null").getData().stream()
                .sorted(Comparator.comparing(Employee::getEmployee_salary).reversed())
                .limit(10)
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
    }

    @Override
    public String createEmployee(Map<String, Object> employeeInput) {
        log.info("Creating new employee with input ::: {}", employeeInput);
        return Objects.requireNonNull(rqChallengeFeignClient.createEmployee(employeeInput).getBody(), "Employee input data cannot be null").getStatus();
    }

    @Override
    public String deleteEmployeeById(String id) {
        log.info("Delete employee by id ::: {}", id);
        return Objects.requireNonNull(rqChallengeFeignClient.deleteEmployeeById(id).getBody(), "Employee with id:" + id + "cannot be null").getStatus();
    }
}
