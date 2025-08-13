package blueMonkey.booking.infraestructure.controller;

import blueMonkey.booking.application.service.BookingService;
import blueMonkey.booking.domain.models.Booking;
import blueMonkey.booking.infraestructure.controller.dtos.input.InputBookingDto;
import blueMonkey.booking.infraestructure.controller.dtos.output.OutputBookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public OutputBookingDto createBooking(@RequestBody InputBookingDto inputBookingDto) {
        return bookingService.createBooking(inputBookingDto);
    }

    @GetMapping
    public List<OutputBookingDto> getBookingsByStatus(@RequestParam Booking.BookingStatus status) {
        return bookingService.getBookingsByStatus(status);
    }

    @GetMapping("/by-email/{email}")
    public List<OutputBookingDto> getBookingsByEmail(@PathVariable String email) {
        return bookingService.getBookingsByEmail(email);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OutputBookingDto updateBookingStatus(@PathVariable Long id,
                                       @RequestParam Booking.BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OutputBookingDto updateBooking(@PathVariable Long id, @RequestBody InputBookingDto BookingDtoInputBookingDto) {
        return bookingService.updateBooking(id, BookingDtoInputBookingDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        return bookingService.deleteBooking(id);
    }

}
