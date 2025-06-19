package blueMonkey.booking.infraestructure.controller;

import blueMonkey.booking.application.service.BookingService;
import blueMonkey.booking.domain.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getBookingsByStatus(@RequestParam Booking.BookingStatus status) {
        return bookingService.getBookingsByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Booking updateBookingStatus(@PathVariable Long id,
                                       @RequestParam Booking.BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }
}
