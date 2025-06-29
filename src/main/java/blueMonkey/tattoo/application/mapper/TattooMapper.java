package blueMonkey.tattoo.application.mapper;

import blueMonkey.tattoo.infraestructure.dtos.input.InputTattooDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTattooDto;
import blueMonkey.tattoo.models.TattooEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TattooMapper {

    // Mapea ProductEntity a OutputProductDto
    OutputTattooDto toDTO(TattooEntity tatuaje);

    // Mapea InputProductDto a ProductEntity
    TattooEntity toEntity(InputTattooDto inputTatuajeDto);

}
