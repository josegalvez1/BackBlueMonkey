package blueMonkey.booking.application.service;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.booking.domain.models.mapper.BookingMapper;
import blueMonkey.booking.infraestructure.controller.dtos.input.InputBookingDto;
import blueMonkey.booking.infraestructure.controller.dtos.output.OutputBookingDto;
import blueMonkey.booking.infraestructure.repository.BookingRepository;
import blueMonkey.security.exceptions.BookingConflictException;
import blueMonkey.user.domain.models.UserEntity;
import blueMonkey.user.infraestructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
@Autowired private BookingMapper bookingMapper;

    public OutputBookingDto createBooking (InputBookingDto inputBookingDto) {
        System.out.println("djdjd");
        UserEntity user = userRepository.findByEmail(inputBookingDto.getEmailUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Booking booking = bookingMapper.toEntity(inputBookingDto);
        booking.setUser(user);

        System.out.println("creando: ");
        List<Booking> conflictingBookings = bookingRepository.findByDateTimeAndStatus(
                booking.getDateTime(), Booking.BookingStatus.APPROVED);

        if (!conflictingBookings.isEmpty()) {
            throw new BookingConflictException("Ya existe una reserva en ese horario");
        }

        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public OutputBookingDto updateBooking(Long id, InputBookingDto inputBookingDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        System.out.println("editando: ");

        if(!inputBookingDto.getClientPhone().isBlank()) booking.setClientPhone(inputBookingDto.getClientPhone());
        if(inputBookingDto.getDateTime() != null) booking.setDateTime(inputBookingDto.getDateTime());
        if(inputBookingDto.getStatus()!=null) booking.setStatus(inputBookingDto.getStatus());
        if(!inputBookingDto.getDetails().isBlank()) booking.setDetails(inputBookingDto.getDetails());
        if(!inputBookingDto.getClientName().isBlank()) booking.setClientName(inputBookingDto.getClientName());

        bookingRepository.save(booking);
        return  bookingMapper.toDTO(booking);
    }

    @Override
    public ResponseEntity<String> deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        bookingRepository.delete(booking);
        return ResponseEntity.status(200).body("Se ha borrado correctamente");
    }

    public Booking updateBookingStatus(Long id, Booking.BookingStatus status) {
        System.out.println("Status recibido: " + status);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
