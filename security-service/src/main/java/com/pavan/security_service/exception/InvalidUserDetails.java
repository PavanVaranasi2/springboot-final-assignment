package com.pavan.security_service.exception;

public class InvalidUserDetails extends RuntimeException{
    public InvalidUserDetails(String message){
        super(message);
    }
}
