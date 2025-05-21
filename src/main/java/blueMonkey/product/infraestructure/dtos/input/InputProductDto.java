package blueMonkey.product.infraestructure.dtos.input;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputProductDto {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Float price;

    private Float priceRegular;

    @NotNull
    private int stock;

    private String imageUrl;

    private String category;

}
