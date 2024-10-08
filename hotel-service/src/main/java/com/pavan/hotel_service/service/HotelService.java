package com.pavan.hotel_service.service;

import com.pavan.hotel_service.exception.HotelNotFoundException;
import com.pavan.hotel_service.exception.InvalidHotelDataException;
import com.pavan.hotel_service.model.Hotel;
import com.pavan.hotel_service.repository.HotelRepository;
import com.pavan.hotel_service.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel addHotel(Hotel hotel) {
        if (hotel.getName() == null || hotel.getName().isEmpty()) {
            throw new InvalidHotelDataException(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION);
        }
        return hotelRepository.save(hotel);
    }

    public Hotel findById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException(Constant.printHotelNotFoundExceptionMessage(id)));
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel updateHotel(Integer id, Hotel updatedHotel) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setId(updatedHotel.getId());
            if (updatedHotel.getName() != null && !updatedHotel.getName().isEmpty()) {
                hotel.setName(updatedHotel.getName());
            } else {
                throw new InvalidHotelDataException(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION);
            }
            hotel.setLocation(updatedHotel.getLocation());
            hotel.setReceptionContactNumber(updatedHotel.getReceptionContactNumber());
            hotel.setEmail(updatedHotel.getEmail());
            hotel.setStarRating(updatedHotel.getStarRating());
            hotel.setDescription(updatedHotel.getDescription());
            hotel.setAmenities(updatedHotel.getAmenities());
            return hotelRepository.save(hotel);
        }).orElseThrow(() -> new HotelNotFoundException(Constant.printHotelNotFoundExceptionMessage(id)));
    }

    public Hotel partiallyUpdateHotel(Integer id, Hotel partialUpdateHotel) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setId(partialUpdateHotel.getId());
            if (partialUpdateHotel.getName() != null && !partialUpdateHotel.getName().isEmpty()) {
                hotel.setName(partialUpdateHotel.getName());
            } else {
                throw new InvalidHotelDataException(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION);
            }
            hotel.setLocation(partialUpdateHotel.getLocation());
            hotel.setReceptionContactNumber(partialUpdateHotel.getReceptionContactNumber());
            hotel.setEmail(partialUpdateHotel.getEmail());
            hotel.setStarRating(partialUpdateHotel.getStarRating());
            hotel.setDescription(partialUpdateHotel.getDescription());
            hotel.setAmenities(partialUpdateHotel.getAmenities());
            return hotelRepository.save(hotel);
        }).orElseThrow(() -> new HotelNotFoundException(Constant.printHotelNotFoundExceptionMessage(id)));
    }

    public void deleteHotel(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(Constant.printHotelNotFoundExceptionMessage(id)));
        hotelRepository.delete(hotel);
    }
}
