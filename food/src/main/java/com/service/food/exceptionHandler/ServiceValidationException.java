package com.service.food.exceptionHandler;

import org.springframework.stereotype.Component;

@Component
public class ServiceValidationException extends RuntimeException {

    private String errorMessage;
    private  int errorCode;

    public String getMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceValidationException(String errorMessage, int errorCode) {
        super();
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ServiceValidationException(){}
}
