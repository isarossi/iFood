package com.recommendation.error;

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
        return new ErrorResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRestException(RestException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}