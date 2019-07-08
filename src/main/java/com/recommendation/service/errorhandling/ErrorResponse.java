package com.recommendation.service.errorhandling;

public class ErrorResponse {
    private String cod;
    private String message;

    public ErrorResponse(String cod, String message) {
        this.message = message;
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse [status=" + cod + ", message=" + message + "]";
    }
}
