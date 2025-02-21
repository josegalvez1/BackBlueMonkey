package blueMonkey.producto.shared.dtos.input;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputProductoDto {

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private Float precio;

    private Float precioRegular;

    @NotNull
    private int stock;

    private String imagenUrl;

    private String categoria;

}
