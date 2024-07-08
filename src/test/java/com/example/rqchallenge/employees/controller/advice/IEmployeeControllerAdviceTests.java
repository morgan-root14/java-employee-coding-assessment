package com.example.rqchallenge.employees.controller.advice;

import com.example.rqchallenge.employees.client.RqChallengeFeignClient;
import com.example.rqchallenge.employees.exception.BadRequestException;
import com.example.rqchallenge.employees.exception.NotFoundException;
import com.example.rqchallenge.employees.exception.TooManyRequestsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IEmployeeControllerAdviceTests {

    @Autowired
    IEmployeeControllerAdvice iEmployeeControllerAdvice;

    @MockBean
    RqChallengeFeignClient rqChallengeFeignClient;

    @BeforeEach
    public void setup() {
        this.iEmployeeControllerAdvice = new IEmployeeControllerAdvice();
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testBadRequestException() {
        when(rqChallengeFeignClient.getAllEmployees())
                .thenThrow(new BadRequestException("1000", "Bad Request"));

        iEmployeeControllerAdvice.handleBadRequestException(new BadRequestException("1002", "Too Many Requests"));
        assertThrows(BadRequestException.class, () -> rqChallengeFeignClient.getAllEmployees());
    }

    @Test
    void testTooManyRequestsException() {
        when(rqChallengeFeignClient.getAllEmployees())
                .thenThrow(new TooManyRequestsException("1001", "Not Found"));

        iEmployeeControllerAdvice.handleTooManyRequestsException(new TooManyRequestsException("1002", "Too Many Requests"));
        assertThrows(TooManyRequestsException.class, () -> rqChallengeFeignClient.getAllEmployees());
    }

    @Test
    void testRuntimeException() {
        when(rqChallengeFeignClient.deleteEmployeeById("1"))
                .thenThrow(new RuntimeException("Could not delete employee due to thread interruption"));

        iEmployeeControllerAdvice.handleRuntimeException(new RuntimeException("Could not delete employee due to thread interruption"));
        assertThrows(RuntimeException.class, () -> rqChallengeFeignClient.deleteEmployeeById("1"));
    }

    @Test
    void testNotFoundException() {
        when(rqChallengeFeignClient.getAllEmployees())
                .thenThrow(new NotFoundException("1001", "Not Found"));

        iEmployeeControllerAdvice.handleNotFoundException(new NotFoundException("1001", "Not Found"));
        assertThrows(NotFoundException.class, () -> rqChallengeFeignClient.getAllEmployees());
    }

}
