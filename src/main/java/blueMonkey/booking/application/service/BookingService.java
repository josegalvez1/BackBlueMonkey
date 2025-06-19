package blueMonkey.booking.application.service;

import blueMonkey.booking.domain.models.Booking;

import java.util.List;

public interface BookingService {

    Booking updateBookingStatus(Long id, Booking.BookingStatus status);
    List<Booking> getBookingsByStatus(Booking.BookingStatus status);
    Booking createBooking (Booking booking);
}
