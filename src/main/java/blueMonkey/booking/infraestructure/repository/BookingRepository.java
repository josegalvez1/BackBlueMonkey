package blueMonkey.booking.infraestructure.repository;

import blueMonkey.booking.domain.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatus(Booking.BookingStatus status);
    List<Booking> findByDateTimeAndStatus(LocalDateTime dateTime, Booking.BookingStatus status);


}
