package com.pavan.hotel_service.util;

public class Constant {

    public static final String HOTELS = "/hotels";

    public static final String HOTEL_BY_ID = "/{id}";

    public static final String UPDATE_WITH_ROOMS_IN_HOTELS = "/with-rooms";

    public static final String PRINT_INVALID_HOTEL_NAME_EXCEPTION = "Hotel name cannot be empty or null";

    public static final String DELETE_HOTEL_SUCCESS_MESSAGE = "Hotel Deleted Successfully.";

    public static String printHotelNotFoundExceptionMessage(Integer id) {
        return "Hotel with id " + id + " not found!";
    }

}
