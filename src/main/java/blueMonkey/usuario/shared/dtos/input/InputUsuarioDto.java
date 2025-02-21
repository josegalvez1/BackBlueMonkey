package blueMonkey.usuario.shared.dtos.input;

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
public class InputUsuarioDto {

    @NotNull
    private String nombre;

    @NotNull
    private String clave;

    @Email
    @NotNull
    private String email;

    private Role role;
}
