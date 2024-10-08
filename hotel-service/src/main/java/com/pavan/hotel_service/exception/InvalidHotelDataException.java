package com.pavan.hotel_service.exception;

public class InvalidHotelDataException extends RuntimeException {
    public InvalidHotelDataException(String message) {
        super(message);
    }
}
