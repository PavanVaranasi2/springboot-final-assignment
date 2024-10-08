package com.pavan.room_service.repository;

import com.pavan.room_service.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByHotelId(Integer hotelId);
}
