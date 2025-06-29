package blueMonkey.booking.domain.models.mapper;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.booking.infraestructure.controller.dtos.input.InputBookingDto;
import blueMonkey.booking.infraestructure.controller.dtos.output.OutputBookingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    OutputBookingDto toDTO(Booking booking);

    Booking toEntity(InputBookingDto inputBookingDto);

}

