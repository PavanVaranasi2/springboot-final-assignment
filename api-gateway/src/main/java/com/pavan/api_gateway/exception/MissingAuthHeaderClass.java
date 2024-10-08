package com.pavan.api_gateway.exception;

public class MissingAuthHeaderClass extends RuntimeException {
    public MissingAuthHeaderClass(String message) {
        super(message);
    }
}
