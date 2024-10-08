package com.pavan.room_service.service;

import com.pavan.room_service.exception.InvalidRoomDataException;
import com.pavan.room_service.exception.RoomNotFoundException;
import com.pavan.room_service.model.Room;
import com.pavan.room_service.repository.RoomRepository;
import com.pavan.room_service.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repo;

    @Autowired
    public RoomService(RoomRepository repo) {
        this.repo = repo;
    }

    public List<Room> getAllRooms() {
        return repo.findAll();
    }

    public Room getRoomById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RoomNotFoundException(Constant.printRoomNotFoundExceptionMessage(id)));
    }

    public List<Room> getRoomsByHotelId(Integer hotelId) {
        return repo.findByHotelId(hotelId);
    }

    public Room addRoom(Room room) {
        if (room.getPrice() <= 0) {
            throw new InvalidRoomDataException(Constant.printPriceLessThanZeroExceptionMessage);
        }
        return repo.save(room);
    }

    public Room updateRoom(Integer id, Room updatedRoom) {
        return repo.findById(id).map(room -> {
            room.setId(updatedRoom.getId());
            room.setRoomType(updatedRoom.getRoomType());
            room.setRoomNumber(updatedRoom.getRoomNumber());
            room.setPrice(updatedRoom.getPrice());
            room.setCapacity(updatedRoom.getCapacity());
            room.setAvailability(updatedRoom.getAvailability());
            room.setFacilities(updatedRoom.getFacilities());
            room.setHotelId(updatedRoom.getHotelId());
            return repo.save(room);
        }).orElseThrow(() -> new RoomNotFoundException(Constant.printRoomNotFoundExceptionMessage(id)));
    }

    public Room partiallyUpdateRoom(Integer id, Room partialUpdateRoom) {
        return repo.findById(id).map(room -> {
            room.setId(partialUpdateRoom.getId());
            room.setRoomType(partialUpdateRoom.getRoomType());
            room.setRoomNumber(partialUpdateRoom.getRoomNumber());
            room.setPrice(partialUpdateRoom.getPrice());
            room.setCapacity(partialUpdateRoom.getCapacity());
            room.setAvailability(partialUpdateRoom.getAvailability());
            room.setFacilities(partialUpdateRoom.getFacilities());
            room.setHotelId(partialUpdateRoom.getHotelId());
            return repo.save(room);
        }).orElseThrow(() -> new RoomNotFoundException(Constant.printRoomNotFoundExceptionMessage(id)));
    }

    public void deleteRoom(Integer id) {
        Room room = repo.findById(id).orElseThrow(() -> new RoomNotFoundException(Constant.printRoomNotFoundExceptionMessage(id)));
        repo.delete(room);
    }

}
