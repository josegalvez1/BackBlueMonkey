package blueMonkey.tattoo.application.mapper;

import blueMonkey.tattoo.infraestructure.dtos.input.InputTatuajeDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTatuajeDto;
import blueMonkey.tattoo.models.TatuajeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TatuajeMapper {

    // Mapea ProductEntity a OutputProductDto
    OutputTatuajeDto toDTO(TatuajeEntity tatuaje);

    // Mapea InputProductDto a ProductEntity
    TatuajeEntity toEntity(InputTatuajeDto inputTatuajeDto);

}
