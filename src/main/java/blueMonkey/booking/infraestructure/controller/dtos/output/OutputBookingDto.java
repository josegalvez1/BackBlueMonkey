package blueMonkey.booking.infraestructure.controller.dtos.output;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputBookingDto {

    private Long id;

    private LocalDateTime dateTime;
    private LocalDateTime endDateTime;
    private String clientName;
    private String clientPhone;
    private String details;
    private Booking.BookingStatus status;

}
