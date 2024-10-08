package com.pavan.hotel_service.client;

import com.pavan.hotel_service.model.Room;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface RoomClient {

    @GetExchange("/rooms/hotel/{hotelId}")
    public List<Room> findByHotel(@PathVariable("hotelId") Integer hotelId);

}
