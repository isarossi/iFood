package com.recommendation.error;

import com.recommendation.properties.ErrorMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
    @Autowired
    ErrorMessageConfig errorMessage;
    String message;

    public ErrorResponse(HttpStatus code) {
        if(code == HttpStatus.NOT_FOUND){
            message = errorMessage.getNotFoundMessage();
        }
        else if(code == HttpStatus.BAD_REQUEST){
            message = errorMessage.getBadRequestMessage();
        }
        else{this.message =  errorMessage.getInternalErrorMessage();
        }
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
