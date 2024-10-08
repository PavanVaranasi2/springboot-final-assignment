package com.pavan.api_gateway.exception;

public class InvalidUserAccessClass extends RuntimeException {
    public InvalidUserAccessClass(String message) {
        super(message);
    }
}
