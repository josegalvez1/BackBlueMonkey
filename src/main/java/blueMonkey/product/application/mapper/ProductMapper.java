package blueMonkey.product.application.mapper;

import blueMonkey.product.domain.models.ProductEntity;
import blueMonkey.product.infraestructure.dtos.input.InputProductDto;
import blueMonkey.product.infraestructure.dtos.output.OutputProductDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Mapea ProductEntity a OutputProductDto
    OutputProductDto toDTO(ProductEntity product);

    // Mapea InputProductDto a ProductEntity
    ProductEntity toEntity(InputProductDto inputProductDto);

}
