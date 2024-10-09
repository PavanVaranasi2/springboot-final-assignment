package com.pavan.room_service.controller;

import com.pavan.room_service.exception.RoomNotFoundException;
import com.pavan.room_service.model.Room;
import com.pavan.room_service.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoomControllerTest {

    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomService roomService;

    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room(1, "Deluxe", 101, 2000.0, 2, true, "WiFi, AC", 1);
    }

    @Test
    void findAll_ShouldReturnListOfRooms() {
        List<Room> rooms = Arrays.asList(room);
        when(roomService.getAllRooms()).thenReturn(rooms);

        List<Room> result = roomController.findAll();

        assertEquals(1, result.size());
        assertEquals("Deluxe", result.get(0).getRoomType());
        verify(roomService, times(1)).getAllRooms();
    }

    @Test
    void add_ShouldReturnAddedRoom() {
        when(roomService.addRoom(any(Room.class))).thenReturn(room);

        Room result = roomController.add(room);

        assertEquals(room, result);
        verify(roomService, times(1)).addRoom(any(Room.class));
    }

    @Test
    void findById_ShouldReturnRoom_WhenRoomExists() {
        when(roomService.getRoomById(anyInt())).thenReturn(room);

        Room result = roomController.findById(1);

        assertEquals(room, result);
        verify(roomService, times(1)).getRoomById(1);
    }

    @Test
    void findById_ShouldThrowRoomNotFoundException_WhenRoomDoesNotExist() {
        when(roomService.getRoomById(anyInt())).thenThrow(new RoomNotFoundException("Room with id 1 not found!"));

        RoomNotFoundException exception = assertThrows(RoomNotFoundException.class, () -> roomController.findById(1));

        assertEquals("Room with id 1 not found!", exception.getMessage());
        verify(roomService, times(1)).getRoomById(1);
    }

    @Test
    void findByHotel_ShouldReturnListOfRooms() {
        List<Room> rooms = Arrays.asList(room);
        when(roomService.getRoomsByHotelId(anyInt())).thenReturn(rooms);

        List<Room> result = roomController.findByHotel(1);

        assertEquals(1, result.size());
        assertEquals(room, result.get(0));
        verify(roomService, times(1)).getRoomsByHotelId(1);
    }

    @Test
    void updateRoom_ShouldReturnUpdatedRoom() {
        when(roomService.updateRoom(anyInt(), any(Room.class))).thenReturn(room);

        Room result = roomController.updateRoom(1, room);

        assertEquals(room, result);
        verify(roomService, times(1)).updateRoom(1, room);
    }

    @Test
    void updateRoom_ShouldThrowRoomNotFoundException_WhenRoomDoesNotExist() {
        when(roomService.updateRoom(anyInt(), any(Room.class))).thenThrow(new RoomNotFoundException("Room with id 1 not found!"));

        RoomNotFoundException exception = assertThrows(RoomNotFoundException.class, () -> roomController.updateRoom(1, room));

        assertEquals("Room with id 1 not found!", exception.getMessage());
        verify(roomService, times(1)).updateRoom(1, room);
    }

    @Test
    void partiallyUpdateRoom_ShouldReturnUpdatedRoom() {
        when(roomService.partiallyUpdateRoom(anyInt(), any(Room.class))).thenReturn(room);

        Room result = roomController.partiallyUpdateRoom(1, room);

        assertEquals(room, result);
        verify(roomService, times(1)).partiallyUpdateRoom(1, room);
    }

    @Test
    void partiallyUpdateRoom_ShouldThrowRoomNotFoundException_WhenRoomDoesNotExist() {
        when(roomService.partiallyUpdateRoom(anyInt(), any(Room.class))).thenThrow(new RoomNotFoundException("Room with id 1 not found!"));

        RoomNotFoundException exception = assertThrows(RoomNotFoundException.class, () -> roomController.partiallyUpdateRoom(1, room));

        assertEquals("Room with id 1 not found!", exception.getMessage());
        verify(roomService, times(1)).partiallyUpdateRoom(1, room);
    }

    @Test
    void deleteRoom_ShouldReturnSuccessMessage() {
        doNothing().when(roomService).deleteRoom(anyInt());

        String result = roomController.deleteRoom(1);

        assertEquals("Room with id 1 has been deleted!", result);
        verify(roomService, times(1)).deleteRoom(1);
    }

    @Test
    void deleteRoom_ShouldThrowRoomNotFoundException_WhenRoomDoesNotExist() {
        doThrow(new RoomNotFoundException("Room with id 1 not found!")).when(roomService).deleteRoom(anyInt());

        RoomNotFoundException exception = assertThrows(RoomNotFoundException.class, () -> roomController.deleteRoom(1));

        assertEquals("Room with id 1 not found!", exception.getMessage());
        verify(roomService, times(1)).deleteRoom(1);
    }

}
