package com.pavan.room_service.util;

public class Constant {

    public static final String ROOMS = "/rooms";

    public static final String ROOM_BY_ID = "/{id}";

    public static final String GET_ALL_ROOM_BY_HOTEL_ID = "/hotel/{hotelId}";

    public static String printPriceLessThanZeroExceptionMessage = "Price must be greater than zero";

    public static String printRoomNotFoundExceptionMessage(Integer id) {
        return "Room with id " + id + " not found!";
    }

}
