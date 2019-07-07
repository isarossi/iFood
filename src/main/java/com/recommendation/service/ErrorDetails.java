package com.recommendation.service;

import java.util.Date;

public class ErrorDetails {
    private int statusCode;
    private String message;

    public ErrorDetails(int statusCode, String message)  {
        this.message = message;
        this.statusCode = statusCode;
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }

}

