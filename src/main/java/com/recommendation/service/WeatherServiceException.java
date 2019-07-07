package com.recommendation.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "City parameter are invalid")
public class WeatherServiceException extends RuntimeException{
    public WeatherServiceException(String exception) {
        super(exception);
    }
}
