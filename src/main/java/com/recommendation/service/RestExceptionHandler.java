package com.recommendation.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@ResponseBody
public class RestExceptionHandler {

    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(Exception ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getCause().getMessage());
    }

    @ExceptionHandler(RestException.class)
    public ErrorResponse handleRestException(Exception ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getCause().getMessage());
    }

}