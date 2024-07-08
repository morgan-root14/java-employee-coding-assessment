package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.RqChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class IEmployeeControllerImplTests {
    @Autowired
    private IEmployeeController iEmployeeController;

    @MockBean
    private RqChallengeService rqChallengeService;

    List<Employee> mockEmployees;

    @BeforeEach
    public void setup() {
        mockEmployees = Arrays.asList(
                new Employee(1, "Morgan Root", 100000, 28, ""),
                new Employee(2, "Andy Dalton", 600000, 35, ""),
                new Employee(3, "Drew Brees", 70000, 49, ""),
                new Employee(4, "Peyton Manning", 35000, 56, "")
        );
        this.iEmployeeController = new IEmployeeControllerImpl(rqChallengeService);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() throws IOException {
        when(rqChallengeService.getAllEmployees()).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> result = iEmployeeController.getAllEmployees();

        assert !(Objects.requireNonNull(result.getBody())).isEmpty();
        assert Objects.equals(result.getBody().get(0).getEmployee_name(), "Morgan Root");
        assert Objects.equals(result.getBody().get(1).getEmployee_name(), "Andy Dalton");
        assert Objects.equals(result.getBody().get(2).getEmployee_name(), "Drew Brees");
        assert Objects.equals(result.getBody().get(3).getEmployee_name(), "Peyton Manning");
    }

    @Test
    void getEmployeesByNameSearch() {
        when(rqChallengeService.getEmployeesByNameSearch("Morgan")).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> result = iEmployeeController.getEmployeesByNameSearch("Morgan");

        assert !(Objects.requireNonNull(result.getBody())).isEmpty();
        assert Objects.equals(result.getBody().get(0).getEmployee_name(), "Morgan Root");
    }


    @Test
    void getEmployeeById() {
        when(rqChallengeService.getEmployeeById("1")).thenReturn(new Employee(1, "Morgan Root", 100000, 28, ""));

        ResponseEntity<Employee> result = iEmployeeController.getEmployeeById("1");

        assert Objects.equals(Objects.requireNonNull(result.getBody()).getEmployee_name(), "Morgan Root");
    }

    @Test
    void getHighestSalaryOfEmployees() {
        when(rqChallengeService.getHighestSalaryOfEmployees()).thenReturn(100000);

        ResponseEntity<Integer> result = iEmployeeController.getHighestSalaryOfEmployees();

        assert result.getBody() == 100000;
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() {
        when(rqChallengeService.getTopTenHighestEarningEmployeeNames()).thenReturn(Arrays.asList("Morgan Root", "Tom Brady", "Andy Dalton"));

        ResponseEntity<List<String>> result = iEmployeeController.getTopTenHighestEarningEmployeeNames();

        assert Objects.equals(Objects.requireNonNull(result.getBody().get(0)), "Morgan Root");
        assert Objects.equals(Objects.requireNonNull(result.getBody().get(1)), "Tom Brady");
        assert Objects.equals(Objects.requireNonNull(result.getBody().get(2)), "Andy Dalton");
    }

    @Test
    void createEmployee() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("id", "10");
        employeeInput.put("name", "Morgan Root");
        employeeInput.put("salary", "100000");
        employeeInput.put("profile_image", "");

        when(rqChallengeService.createEmployee(employeeInput)).thenReturn("success");

        ResponseEntity<String> result = iEmployeeController.createEmployee(employeeInput);

        assert Objects.equals(Objects.requireNonNull(result.getBody()), "success");
    }

    @Test
    void deleteEmployeeById() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("id", "10");
        employeeInput.put("name", "Morgan Root");
        employeeInput.put("salary", "100000");
        employeeInput.put("profile_image", "");

        when(rqChallengeService.deleteEmployeeById("1")).thenReturn("Morgan Root");

        ResponseEntity<String> result = iEmployeeController.deleteEmployeeById("1");

        assert Objects.equals(Objects.requireNonNull(result.getBody()), "Morgan Root");
    }
}
