package com.pavan.hotel_service.Controller;

import com.pavan.hotel_service.client.RoomClient;
import com.pavan.hotel_service.model.Hotel;
import com.pavan.hotel_service.service.HotelService;
import com.pavan.hotel_service.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.HOTELS)
public class HotelController {

    private static final Logger LOGGER= LoggerFactory.getLogger(HotelController.class);
    private final HotelService service;
    private final RoomClient roomClient;

    @Autowired
    public HotelController(HotelService service, RoomClient roomClient) {
        this.service = service;
        this.roomClient = roomClient;
    }

    @PostMapping
    public Hotel add(@RequestBody Hotel hotel) {
        LOGGER.info("Adding Hotel: {}", hotel);
        return service.addHotel(hotel);
    }

    @GetMapping
    public List<Hotel> findAll() {
        LOGGER.info("Getting All Hotels....");
        return service.findAll();
    }

    @GetMapping(Constant.HOTEL_BY_ID)
    public Hotel findById(@PathVariable Integer id) {
        LOGGER.info("Getting Hotel info with id: {}", id);
        return service.findById(id);
    }

    @GetMapping(Constant.UPDATE_WITH_ROOMS_IN_HOTELS)
    public List<Hotel> findAllWithRooms() {
        LOGGER.info("Hotels are getting updated with rooms information.");
        List<Hotel> hotels = service.findAll();
        hotels.forEach(hotel->hotel.setRooms(roomClient.findByHotel(hotel.getId())));
        return hotels;
    }

    @PutMapping(Constant.HOTEL_BY_ID)
    public Hotel updateHotel(@PathVariable("id") Integer id, Hotel updatedHotel) {
        LOGGER.info("Hotels info getting updated with Put Mapping");
        return service.updateHotel(id, updatedHotel);
    }

    @PatchMapping(Constant.HOTEL_BY_ID)
    public Hotel partiallyUpdateHotel(@PathVariable("id") Integer id, Hotel updatedHotel) {
        LOGGER.info("Hotels info getting updated with Patch Mapping");
        return service.partiallyUpdateHotel(id, updatedHotel);
    }

    @DeleteMapping(Constant.HOTEL_BY_ID)
    public String deleteHotel(@PathVariable("id") Integer id) {
        LOGGER.info("Hotels info getting deleted with Delete Mapping");
        service.deleteHotel(id);
        return Constant.DELETE_HOTEL_SUCCESS_MESSAGE;
    }

}
