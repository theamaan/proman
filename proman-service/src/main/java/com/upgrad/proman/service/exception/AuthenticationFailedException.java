package com.upgrad.proman.service.exception;

public class AuthenticationFailedException extends Exception{

    private String code;
    private String errorMessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AuthenticationFailedException(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
