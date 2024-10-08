package com.pavan.room_service.controller;

import com.pavan.room_service.model.Room;
import com.pavan.room_service.service.RoomService;
import com.pavan.room_service.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.ROOMS)
public class RoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);
    private final RoomService service;

    @Autowired
    public RoomController(RoomService roomService) {
        this.service = roomService;
    }

    @GetMapping
    public List<Room> findAll() {
        LOGGER.info("Getting all Rooms info");
        return service.getAllRooms();
    }

    @PostMapping
    public Room add(@RequestBody Room room) {
        LOGGER.info("Adding room: {}", room);
        return service.addRoom(room);
    }

    @GetMapping(Constant.ROOM_BY_ID)
    public Room findById(@PathVariable("id") Integer id) {
        LOGGER.info("Finding room by id: {}", id);
        return service.getRoomById(id);
    }

    @GetMapping(Constant.GET_ALL_ROOM_BY_HOTEL_ID)
    public List<Room> findByHotel(@PathVariable("hotelId") Integer hotelId) {
        LOGGER.info("Finding rooms by hotelId: {}", hotelId);
        return service.getRoomsByHotelId(hotelId);
    }

    @PutMapping(Constant.ROOM_BY_ID)
    public Room updateRoom(@PathVariable("id") Integer id, @RequestBody Room updatedRoom) {
        LOGGER.info("Updating room with id: {}", id);
        return service.updateRoom(id, updatedRoom);
    }

    @PatchMapping(Constant.ROOM_BY_ID)
    public Room partiallyUpdateRoom(@PathVariable("id") Integer id, @RequestBody Room partialUpdateRoom) {
        LOGGER.info("Partially updating room with id: {}", id);
        return service.partiallyUpdateRoom(id, partialUpdateRoom);
    }

    @DeleteMapping(Constant.ROOM_BY_ID)
    public String deleteRoom(@PathVariable("id") Integer id) {
        LOGGER.info("Deleting room with id: {}", id);
        service.deleteRoom(id);
        return "Room with id " + id + " has been deleted!";
    }

}
