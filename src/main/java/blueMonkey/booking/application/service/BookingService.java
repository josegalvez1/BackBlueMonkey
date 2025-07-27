package blueMonkey.booking.application.service;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.booking.infraestructure.controller.dtos.input.InputBookingDto;
import blueMonkey.booking.infraestructure.controller.dtos.output.OutputBookingDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    OutputBookingDto updateBooking(Long id, InputBookingDto inputBookingDto);
    ResponseEntity<String> deleteBooking(Long id);
    OutputBookingDto updateBookingStatus(Long id, Booking.BookingStatus status);
    List<OutputBookingDto> getBookingsByStatus(Booking.BookingStatus status);
   OutputBookingDto createBooking (InputBookingDto inputBookingDto);
    List<OutputBookingDto> getBookingsByEmail(String email);
}
