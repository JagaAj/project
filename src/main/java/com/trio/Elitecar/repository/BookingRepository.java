package com.trio.Elitecar.repository;


import com.trio.Elitecar.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndStatus(Long id, String status);

    // Get all bookings where status is NOT Approved
    List<Booking> findByStatusNot(String status);
}
