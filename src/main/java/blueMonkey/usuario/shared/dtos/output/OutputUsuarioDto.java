package blueMonkey.usuario.shared.dtos.output;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.management.relation.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputUsuarioDto {

    private Long id;

    private String nombre;

    private String email;

    private Role role;
}
