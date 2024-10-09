package com.pavan.hotel_service.service;

import com.pavan.hotel_service.exception.HotelNotFoundException;
import com.pavan.hotel_service.exception.InvalidHotelDataException;
import com.pavan.hotel_service.model.Hotel;
import com.pavan.hotel_service.repository.HotelRepository;
import com.pavan.hotel_service.util.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hotel = new Hotel(1, "Test Hotel", "Location", "1234567890", "test@example.com", 5, "A nice hotel", 10, "Wi-Fi, Breakfast", null);
    }

    @Test
    void testAddHotel_Success() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        Hotel savedHotel = hotelService.addHotel(hotel);
        assertNotNull(savedHotel);
        assertEquals(hotel.getName(), savedHotel.getName());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void testAddHotel_InvalidName_Null() {
        Hotel invalidHotel = new Hotel();
        invalidHotel.setName(null); // Invalid name (null)

        Exception exception = assertThrows(InvalidHotelDataException.class, () -> hotelService.addHotel(invalidHotel));
        assertEquals(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void testAddHotel_InvalidName_Empty() {
        Hotel invalidHotel = new Hotel();
        invalidHotel.setName(""); // Invalid name (empty string)

        Exception exception = assertThrows(InvalidHotelDataException.class, () -> hotelService.addHotel(invalidHotel));
        assertEquals(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void testFindAll() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel));
        List<Hotel> hotels = hotelService.findAll();
        assertNotNull(hotels);
        assertEquals(1, hotels.size());
        assertEquals(hotel.getName(), hotels.get(0).getName());
    }

    @Test
    void testFindById_Success() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));
        Hotel foundHotel = hotelService.findById(1);
        assertNotNull(foundHotel);
        assertEquals(hotel.getId(), foundHotel.getId());
    }

    @Test
    void testFindById_NotFound() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> hotelService.findById(1));
        assertEquals(Constant.printHotelNotFoundExceptionMessage(1), exception.getMessage());
    }

    @Test
    void testUpdateHotel_Success() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        Hotel updatedHotel = new Hotel(1, "Updated Hotel", "Updated Location", "0987654321", "updated@example.com", 4, "Updated description", 5, "Wi-Fi, Breakfast", null);
        Hotel result = hotelService.updateHotel(1, updatedHotel);
        assertEquals(updatedHotel.getName(), result.getName());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void testUpdateHotel_NotFound() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> hotelService.updateHotel(1, hotel));
        assertEquals(Constant.printHotelNotFoundExceptionMessage(1), exception.getMessage());
    }

    @Test
    void testUpdateHotel_InvalidName_Null() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));

        Hotel updatedHotel = new Hotel();
        updatedHotel.setName(null);

        Exception exception = assertThrows(InvalidHotelDataException.class, () -> hotelService.updateHotel(1, updatedHotel));
        assertEquals(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void testUpdateHotel_InvalidName_Empty() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));

        Hotel updatedHotel = new Hotel();
        updatedHotel.setName(""); // Invalid name (empty string)

        Exception exception = assertThrows(InvalidHotelDataException.class, () -> hotelService.updateHotel(1, updatedHotel));
        assertEquals(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void testPartiallyUpdateHotel_Success() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        Hotel partialUpdateHotel = new Hotel();
        partialUpdateHotel.setName("Partially Updated Hotel");
        Hotel result = hotelService.partiallyUpdateHotel(1, partialUpdateHotel);
        assertEquals(partialUpdateHotel.getName(), result.getName());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void testPartiallyUpdateHotel_NotFound() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> hotelService.partiallyUpdateHotel(1, hotel));
        assertEquals(Constant.printHotelNotFoundExceptionMessage(1), exception.getMessage());
    }

    @Test
    void testPartiallyUpdateHotel_InvalidName() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));

        Hotel partialUpdateHotel = new Hotel();
        partialUpdateHotel.setName(null);

        Exception exception = assertThrows(InvalidHotelDataException.class, () -> hotelService.partiallyUpdateHotel(1, partialUpdateHotel));
        assertEquals(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION, exception.getMessage());

        assertEquals("Test Hotel", hotel.getName());
    }

    @Test
    void testPartiallyUpdateHotel_EmptyName() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));

        Hotel partialUpdateHotel = new Hotel();
        partialUpdateHotel.setName("");

        Exception exception = assertThrows(InvalidHotelDataException.class, () -> hotelService.partiallyUpdateHotel(1, partialUpdateHotel));
        assertEquals(Constant.PRINT_INVALID_HOTEL_NAME_EXCEPTION, exception.getMessage());

        assertEquals("Test Hotel", hotel.getName());
    }

    @Test
    void testDeleteHotel_Success() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotel));
        hotelService.deleteHotel(1);
        verify(hotelRepository, times(1)).delete(hotel);
    }

    @Test
    void testDeleteHotel_NotFound() {
        when(hotelRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> hotelService.deleteHotel(1));
        assertEquals(Constant.printHotelNotFoundExceptionMessage(1), exception.getMessage());
    }
}
