package blueMonkey.user.domain.models;

import blueMonkey.security.entity.RolesEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String password;

    @Email
    @NotNull
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persona_roles", joinColumns = @JoinColumn(name = "persona_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles = new HashSet<>();

}
