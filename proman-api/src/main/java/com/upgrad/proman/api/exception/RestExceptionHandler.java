package com.upgrad.proman.api.exception;

import com.upgrad.proman.api.model.ErrorResponse;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException exe, WebRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setCode(exe.getCode());
        response.setMessage(exe.getErrorMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException(AuthenticationFailedException exe, WebRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setCode(exe.getCode());
        response.setMessage(exe.getErrorMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> autthorizationFailedException(UnAuthorizedException exe, WebRequest request){
        ErrorResponse response=new ErrorResponse();
        response.setCode(exe.getCode());
        response.setMessage(exe.getErrorMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
