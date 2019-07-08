package com.recommendation.service.errorhandling;

public class RestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;

    public RestException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
