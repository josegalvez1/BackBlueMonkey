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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired private BookingMapper bookingMapper;

    public OutputBookingDto createBooking(InputBookingDto inputBookingDto) {
        System.out.println("📩 Email recibido: " + inputBookingDto.getEmailUser());

        UserEntity user = userRepository.findByEmail(inputBookingDto.getEmailUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Booking booking = bookingMapper.toEntity(inputBookingDto);
        booking.setUser(user);

        System.out.println("📆 Validando solapamiento para: " + booking.getDateTime() + " - " + booking.getEndDateTime());

        List<Booking> conflictingBookings = bookingRepository.findByStatus(Booking.BookingStatus.APPROVED)
                .stream()
                .filter(existing -> {
                    LocalDateTime existingStart = existing.getDateTime();
                    LocalDateTime existingEnd = existing.getEndDateTime();

                    return booking.getDateTime().isBefore(existingEnd)
                            && booking.getEndDateTime().isAfter(existingStart);
                })
                .toList();

        if (!conflictingBookings.isEmpty()) {
            throw new BookingConflictException("Ya existe una reserva en ese horario");
        }

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDTO(saved);
    }

    public List<OutputBookingDto> getBookingsByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Booking> bookings = bookingRepository.findByUser(user);

        return bookings.stream()
                .map(bookingMapper::toDTO)
                .toList();
    }


    public List<OutputBookingDto> getBookingsByStatus(Booking.BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);

        // Map manually or with a safe DTO mapper
        return bookings.stream()
                .map(booking -> new OutputBookingDto(
                        booking.getId(),
                        booking.getDateTime(),
                        booking.getEndDateTime(),
                        booking.getClientName(),
                        booking.getClientPhone(),
                        booking.getDetails(),
                        booking.getStatus()
                ))
                .collect(Collectors.toList());
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
        System.out.println("id recibido: " + id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        bookingRepository.delete(booking);
        return ResponseEntity.status(200).body("Se ha borrado correctamente");
    }

    public OutputBookingDto updateBookingStatus(Long id, Booking.BookingStatus status) {
        System.out.println("Status recibido: " + status);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        booking.setStatus(status);
         bookingRepository.save(booking);
        return bookingMapper.toDTO(booking);
    }
}
