package com.pavan.hotel_service.Controller;

import com.pavan.hotel_service.client.RoomClient;
import com.pavan.hotel_service.model.Hotel;
import com.pavan.hotel_service.service.HotelService;
import com.pavan.hotel_service.util.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class HotelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    @Mock
    private RoomClient roomClient;

    @InjectMocks
    private HotelController hotelController;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();

        // Initialize a Hotel object
        hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel ABC");
        hotel.setLocation("Hyderabad");
        hotel.setReceptionContactNumber("1234567890");
        hotel.setEmail("hotelabc@example.com");
        hotel.setStarRating(5);
        hotel.setDescription("A luxury hotel");
        hotel.setTotalRooms(100);
        hotel.setAmenities("Pool, Gym, Free Wifi");
    }

    @Test
    void testFindAll() throws Exception {
        List<Hotel> hotels = Arrays.asList(hotel);
        when(hotelService.findAll()).thenReturn(hotels);

        mockMvc.perform(get(Constant.HOTELS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel ABC"))
                .andExpect(jsonPath("$[0].location").value("Hyderabad"));

        verify(hotelService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(hotelService.findById(1)).thenReturn(hotel);

        mockMvc.perform(get(Constant.HOTELS + Constant.HOTEL_BY_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel ABC"))
                .andExpect(jsonPath("$.location").value("Hyderabad"));

        verify(hotelService, times(1)).findById(1);
    }

    @Test
    void testAddHotel() throws Exception {
        when(hotelService.addHotel(any(Hotel.class))).thenReturn(hotel);

        mockMvc.perform(post(Constant.HOTELS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(hotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel ABC"))
                .andExpect(jsonPath("$.location").value("Hyderabad"));

        verify(hotelService, times(1)).addHotel(any(Hotel.class));
    }

    @Test
    void testUpdateHotel() throws Exception {
        when(hotelService.updateHotel(eq(1), any(Hotel.class))).thenReturn(hotel);

        mockMvc.perform(put(Constant.HOTELS + Constant.HOTEL_BY_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(hotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel ABC"))
                .andExpect(jsonPath("$.location").value("Hyderabad"));

        verify(hotelService, times(1)).updateHotel(eq(1), any(Hotel.class));
    }

    @Test
    void testPartiallyUpdateHotel() throws Exception {
        when(hotelService.partiallyUpdateHotel(eq(1), any(Hotel.class))).thenReturn(hotel);

        mockMvc.perform(patch(Constant.HOTELS + Constant.HOTEL_BY_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(hotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel ABC"))
                .andExpect(jsonPath("$.location").value("Hyderabad"));

        verify(hotelService, times(1)).partiallyUpdateHotel(eq(1), any(Hotel.class));
    }

    @Test
    void testDeleteHotel() throws Exception {
        doNothing().when(hotelService).deleteHotel(1);

        mockMvc.perform(delete(Constant.HOTELS + Constant.HOTEL_BY_ID, 1)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(Constant.DELETE_HOTEL_SUCCESS_MESSAGE));

        verify(hotelService, times(1)).deleteHotel(1);
    }


    @Test
    void testFindAllWithRooms() throws Exception {
        List<Hotel> hotels = Arrays.asList(hotel);
        when(hotelService.findAll()).thenReturn(hotels);
        when(roomClient.findByHotel(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(Constant.HOTELS + Constant.UPDATE_WITH_ROOMS_IN_HOTELS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel ABC"))
                .andExpect(jsonPath("$[0].rooms").isArray());

        verify(hotelService, times(1)).findAll();
        verify(roomClient, times(1)).findByHotel(1);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
