package blueMonkey.booking.application.service;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.booking.infraestructure.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;


    public Booking createBooking (Booking booking) {
        booking.setStatus(Booking.BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    public Booking updateBookingStatus( Long id,Booking.BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
