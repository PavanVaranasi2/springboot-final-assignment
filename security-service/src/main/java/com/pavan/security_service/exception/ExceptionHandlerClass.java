package com.pavan.security_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler
    public ResponseEntity<CustomExceptionClass> handleException(InvalidUserException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<CustomExceptionClass> handleException(InvalidRequestException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<CustomExceptionClass> handleInvalidDetailsExceptions(InvalidUserDetails exc) {
        CustomExceptionClass custom = new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<CustomExceptionClass> handleException(InvalidTokenException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<CustomExceptionClass> handleOtherExceptions(Exception exc) {
        CustomExceptionClass custom = new CustomExceptionClass();
        custom.setMessage("An unexpected error occurred.");
        custom.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<CustomExceptionClass> handleOtherExceptions(UserAlreadyExists exc) {
        CustomExceptionClass custom = new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.CONFLICT.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
