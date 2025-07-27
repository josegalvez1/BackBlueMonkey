package blueMonkey.booking.infraestructure.controller.dtos.input;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputBookingDto {

    private LocalDateTime dateTime;
    private LocalDateTime endDateTime;
    private String clientName;
    private String clientPhone;
    private String details;
    private Booking.BookingStatus status;

    @JsonProperty("email")  // 👈 Esto enlaza con el campo del JSON
    private String emailUser;

}
