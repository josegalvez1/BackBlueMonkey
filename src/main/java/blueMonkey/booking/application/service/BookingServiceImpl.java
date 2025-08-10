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

        // Validar solapamientos con citas aprobadas
        List<Booking> conflictingApproved = bookingRepository.findByStatus(Booking.BookingStatus.APPROVED)
                .stream()
                .filter(existing -> {
                    LocalDateTime existingStart = existing.getDateTime();
                    LocalDateTime existingEnd = existing.getEndDateTime();

                    return booking.getDateTime().isBefore(existingEnd)
                            && booking.getEndDateTime().isAfter(existingStart);
                })
                .toList();

        if (!conflictingApproved.isEmpty()) {
            throw new BookingConflictException("Ya existe una reserva en ese horario");
        }

        // Si la nueva reserva es APPROVED, eliminar citas FREE que se solapen
        if (inputBookingDto.getStatus() == Booking.BookingStatus.APPROVED) {
            List<Booking> overlappingFree = bookingRepository.findByStatus(Booking.BookingStatus.FREE)
                    .stream()
                    .filter(existing -> {
                        LocalDateTime existingStart = existing.getDateTime();
                        LocalDateTime existingEnd = existing.getEndDateTime();

                        return booking.getDateTime().isBefore(existingEnd)
                                && booking.getEndDateTime().isAfter(existingStart);
                    })
                    .toList();

            if (!overlappingFree.isEmpty()) {
                bookingRepository.deleteAll(overlappingFree);
                System.out.println("🗑️ Citas libres eliminadas: " + overlappingFree.size());
            }
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
        System.out.println("estoy actualizando");
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        // Validar conflictos solo si se cambia a APPROVED desde otro estado
        if (booking.getStatus() != Booking.BookingStatus.APPROVED &&
                status == Booking.BookingStatus.APPROVED) {

            List<Booking> conflictingBookings = bookingRepository.findByStatus(Booking.BookingStatus.APPROVED)
                    .stream()
                    .filter(existing -> {
                        boolean isNotSameBooking = !existing.getId().equals(booking.getId());
                        boolean overlaps = booking.getDateTime().isBefore(existing.getEndDateTime()) &&
                                booking.getEndDateTime().isAfter(existing.getDateTime());
                        return isNotSameBooking && overlaps;
                    })
                    .toList();

            if (!conflictingBookings.isEmpty()) {
                throw new BookingConflictException("Ya existe una reserva en ese horario");
            }
        }

        // Actualizar el estado
        booking.setStatus(status);
        bookingRepository.save(booking);

        // Si el estado es APPROVED, eliminar la cita libre que se solape en ese horario
        if (status == Booking.BookingStatus.APPROVED) {
            // Buscar citas libres que se solapen con esta cita aprobada
            List<Booking> freeBookingsToDelete = bookingRepository.findByStatus(Booking.BookingStatus.FREE)
                    .stream()
                    .filter(free ->
                            free.getDateTime().isBefore(booking.getEndDateTime()) &&
                                    free.getEndDateTime().isAfter(booking.getDateTime())
                    )
                    .toList();

            // Eliminar esas citas libres
            freeBookingsToDelete.forEach(free -> {
                bookingRepository.delete(free);
                System.out.println("Cita libre eliminada: " + free.getId());
            });
        }

        return bookingMapper.toDTO(booking);
    }

}
