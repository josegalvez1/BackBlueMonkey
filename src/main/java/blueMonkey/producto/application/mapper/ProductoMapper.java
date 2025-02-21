package blueMonkey.producto.application.mapper;

import blueMonkey.producto.domain.models.Producto;
import blueMonkey.producto.shared.dtos.input.InputProductoDto;
import blueMonkey.producto.shared.dtos.output.OutputProductoDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    // Mapea ProductEntity a OutputProductDto
    OutputProductoDto toDTO(Producto producto);

    // Mapea InputProductDto a ProductEntity
    Producto toEntity(InputProductoDto inputProductoDto);

}
