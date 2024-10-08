package com.pavan.security_service.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message){
        super(message);
    }
}
