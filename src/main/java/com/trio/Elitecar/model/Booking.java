package com.trio.Elitecar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String carName;
    private String userLocation;
    private LocalDateTime pickupDatetime;
    private LocalDateTime dropDatetime;
    private Double totalPrice;
    private String status = "Pending";  // default
    // Getters and Setters
}
