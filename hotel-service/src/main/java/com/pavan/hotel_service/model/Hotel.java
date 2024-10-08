package com.pavan.hotel_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "reception_contact_number", nullable = false)
    private String receptionContactNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "star_rating")
    private int starRating;

    @Column(name = "description")
    private String description;

    @Column(name = "total_rooms")
    private int totalRooms;

    @Column(name = "amenities")
    private String amenities;

    @OneToMany(mappedBy = "hotelId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();

    // Getters and Setters

    // Constructors
}
