package blueMonkey.booking.domain.models;

import blueMonkey.user.domain.models.UserEntity;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
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
    private LocalDateTime endDateTime;

    private String clientName;
    private String clientPhone;
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    public enum BookingStatus {
        PENDING,
        APPROVED,
        REJECTED,
        FREE
    }
}
