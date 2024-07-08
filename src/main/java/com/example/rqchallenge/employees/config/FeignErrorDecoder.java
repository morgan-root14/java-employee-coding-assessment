package com.example.rqchallenge.employees.config;

import com.example.rqchallenge.employees.exception.BadRequestException;
import com.example.rqchallenge.employees.exception.InternalServerErrorException;
import com.example.rqchallenge.employees.exception.NotFoundException;
import com.example.rqchallenge.employees.exception.TooManyRequestsException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("There was an error retrieving values from the client. MethodKey: {}, HttpCode: {}, HttpResponse: {}", methodKey, response.status(), response.reason());
        return switch (response.status()) {
            case 400 -> new BadRequestException("1000", response.reason());
            case 404 -> new NotFoundException("1001", response.reason());
            case 429 -> new TooManyRequestsException("1002", response.reason());
            default -> new InternalServerErrorException("1003", response.reason());
        };
    }
}
