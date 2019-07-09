package com.recommendation.error;

import com.recommendation.properties.ErrorMessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@ResponseBody
public class RestExceptionHandler {
    @Autowired
    ErrorMessageProperties errorMessageProperties;

    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(Exception ex) {
        return new ErrorResponse(errorMessageProperties.getBadRequestMessage());
    }

    @ExceptionHandler(RestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRestException(RestException ex) {
        return new ErrorResponse(errorMessageProperties.getNotFoundMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception ex) {
        return new ErrorResponse(errorMessageProperties.getInternalErrorMessage());
    }

}