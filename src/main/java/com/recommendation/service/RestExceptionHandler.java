package com.recommendation.service;

import com.google.gson.Gson;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getCause().getMessage());
    }

    @ExceptionHandler(RestException.class)
    public ErrorResponse handleRestException(RestException ex) {
        Gson gson = new Gson();
        ErrorResponse errorResponse = gson.fromJson(ex.getMsg(), ErrorResponse.class);
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.toString());
    }

}