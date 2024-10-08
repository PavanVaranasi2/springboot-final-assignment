package com.pavan.room_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @Column(name = "facilities")
    private String facilities;

    @Column(name = "hotel_id")
    private Integer hotelId;

    // Getters and Setters

    // Constructors
}
