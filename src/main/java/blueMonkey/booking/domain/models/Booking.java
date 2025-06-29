package blueMonkey.booking.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private String clientName;
    private String clientPhone;
    private String details;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    public enum BookingStatus {
        PENDING,
        APPROVED,
        REJECTED,
        FREE
    }
}
