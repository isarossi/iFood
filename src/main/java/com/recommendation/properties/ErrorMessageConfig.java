package com.recommendation.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "error.message")
public class ErrorMessageConfig {
    private String badRequestMessage;
    private String notFoundMessage;
    private String internalErrorMessage;

    public String getBadRequestMessage() {
        return badRequestMessage;
    }

    public void setBadRequestMessage(String badRequestMessage) {
        this.badRequestMessage = badRequestMessage;
    }

    public String getNotFoundMessage() {
        return notFoundMessage;
    }

    public void setNotFoundMessage(String notFoundMessage) {
        this.notFoundMessage = notFoundMessage;
    }

    public String getInternalErrorMessage() {
        return internalErrorMessage;
    }

    public void setInternalErrorMessage(String internalErrorMessage) {
        this.internalErrorMessage = internalErrorMessage;
    }
}
