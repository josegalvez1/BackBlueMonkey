package blueMonkey.tattoo.infraestructure.dtos.output;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputTatuajeDto {

    private Long id;

    private String name;

    private String category;

    private String imageUrl;

    private String size;

    private String bodyArea;

}
