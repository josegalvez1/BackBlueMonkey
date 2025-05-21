package blueMonkey.product.infraestructure.dtos.output;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputProductDto {

    private Long id;

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
