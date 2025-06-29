package blueMonkey.tattoo.infraestructure.dtos.input;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputTattooDto {

    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    private String category;

    private String imageUrl;

    private String size;

    private String bodyArea;


}
