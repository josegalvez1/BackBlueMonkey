package blueMonkey.user.infraestructure.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.management.relation.Role;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputUserDto {

    @NotNull
    private String name;

    @NotNull
    private String password;

    @Email
    @NotNull(message = "El email es incorrecto")
    private String email;

    private List<String> roles;

}
