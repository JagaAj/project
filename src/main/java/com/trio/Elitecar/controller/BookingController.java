package com.trio.Elitecar.controller;

import com.trio.Elitecar.model.Booking;
import com.trio.Elitecar.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:5173/") // adjust if needed
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingRepository.save(booking);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setStatus(payload.get("status"));
            return ResponseEntity.ok(bookingRepository.save(booking));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}/ongoing")
    public ResponseEntity<Booking> getOngoingBooking(@PathVariable Long id) {
        return bookingRepository.findByIdAndStatus(id, "Approved")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}/history")
    public List<Booking> getPreviousBookings(@PathVariable Long id) {
        // return all non-approved bookings (ignoring user now)
        return bookingRepository.findByStatusNot("Approved");
    }

}
