package com.example.rqchallenge.employees.service;


import com.example.rqchallenge.employees.client.RqChallengeFeignClient;
import com.example.rqchallenge.employees.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@SpringBootTest
class RqChallengeServiceImplTests {
    @Autowired
    private RqChallengeService rqChallengeService;

    @MockBean
    private RqChallengeFeignClient rqChallengeFeignClient;

    @Mock
    GetEmployeeListResponse getEmployeeListResponse;

    @Mock
    EmployeeResponse employeeResponse;

    @Mock
    CreateEmployeeResponse createEmployeeResponse;

    @Mock
    DeleteEmployeeResponse deleteEmployeeResponse;

    Employee employee;

    List<Employee> mockEmployees;

    @BeforeEach
    public void setup() {
        mockEmployees = Arrays.asList(new Employee(1, "Morgan Root", 100000, 28, ""), new Employee(2, "Andy Dalton", 600000, 35, ""), new Employee(3, "Drew Brees", 70000, 49, ""), new Employee(4, "Peyton Manning", 35000, 56, ""));
        employee = new Employee(1, "Morgan Root", 100000, 28, "");

        this.rqChallengeService = new RqChallengeServiceImpl(rqChallengeFeignClient);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        when(getEmployeeListResponse.getData()).thenReturn(mockEmployees);
        when(rqChallengeFeignClient.getAllEmployees()).thenReturn(ResponseEntity.ok(getEmployeeListResponse));

        List<Employee> result = rqChallengeService.getAllEmployees();

        assert !(result).isEmpty();
        assert Objects.equals(result.get(0).getEmployee_name(), "Morgan Root");
        assert Objects.equals(result.get(1).getEmployee_name(), "Andy Dalton");
        assert Objects.equals(result.get(2).getEmployee_name(), "Drew Brees");
        assert Objects.equals(result.get(3).getEmployee_name(), "Peyton Manning");
    }

    @Test
    void testGetEmployeesByName() {
        when(getEmployeeListResponse.getData()).thenReturn(mockEmployees);
        when(rqChallengeFeignClient.getAllEmployees()).thenReturn(ResponseEntity.ok(getEmployeeListResponse));

        List<Employee> result = rqChallengeService.getEmployeesByNameSearch("Morgan");

        assert !(result).isEmpty();
        assert Objects.equals(result.get(0).getEmployee_name(), "Morgan Root");
    }

    @Test
    void testGetEmployeesById() {
        when(employeeResponse.getData()).thenReturn(employee);
        when(rqChallengeFeignClient.getEmployeeById("1")).thenReturn(ResponseEntity.ok(employeeResponse));

        Employee result = rqChallengeService.getEmployeeById("1");

        assert result != null;
        assert Objects.equals(result.getEmployee_name(), "Morgan Root");
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(getEmployeeListResponse.getData()).thenReturn(mockEmployees);
        when(rqChallengeFeignClient.getAllEmployees()).thenReturn(ResponseEntity.ok(getEmployeeListResponse));

        Integer result = rqChallengeService.getHighestSalaryOfEmployees();

        assert result != null;
        assert result.equals(600000);
    }

    @Test
    void testGetTopTenHighestEarningEmployeesByNames() {
        when(getEmployeeListResponse.getData()).thenReturn(mockEmployees);
        when(rqChallengeFeignClient.getAllEmployees()).thenReturn(ResponseEntity.ok(getEmployeeListResponse));

        List<String> result = rqChallengeService.getTopTenHighestEarningEmployeeNames();

        assert result != null;
        assert Objects.equals(result.get(0), "Andy Dalton");
        assert Objects.equals(result.get(1), "Morgan Root");
        assert Objects.equals(result.get(2), "Drew Brees");
        assert Objects.equals(result.get(3), "Peyton Manning");
    }

    @Test
    void testCreateEmployee() {
        when(createEmployeeResponse.getStatus()).thenReturn("success");
        when(rqChallengeFeignClient.createEmployee(anyMap())).thenReturn(ResponseEntity.ok(createEmployeeResponse));
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("id", "10");
        employeeInput.put("name", "Morgan Root");
        employeeInput.put("salary", "100000");
        employeeInput.put("profile_image", "");
        String result = rqChallengeService.createEmployee(employeeInput);

        assert result != null;
        assert result.equals("success");
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeResponse.getData()).thenReturn(employee);
        when(rqChallengeFeignClient.getEmployeeById("1")).thenReturn(ResponseEntity.ok(employeeResponse));
        when(rqChallengeFeignClient.deleteEmployeeById("1")).thenReturn(ResponseEntity.ok(deleteEmployeeResponse));

        String result = rqChallengeService.deleteEmployeeById("1");

        assert result != null;
        assert result.equals("Morgan Root");
    }

    @Test
    void testDeleteEmployeeById_RuntimeException() throws InterruptedException {
        when(rqChallengeFeignClient.getEmployeeById("1")).thenThrow(new RuntimeException("Could not delete employee due to thread interruption"));
        Throwable exception = assertThrows(RuntimeException.class, () -> rqChallengeService.deleteEmployeeById("1"));
        assertEquals("java.lang.RuntimeException: Could not delete employee due to thread interruption", exception.getMessage());
    }


}
