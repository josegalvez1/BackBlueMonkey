package blueMonkey.user.infraestructure.dtos.output;

import lombok.*;

import javax.management.relation.Role;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputUserDto {

    private Long id;

    private String name;

    private String email;

    private List<String> roles;
}
