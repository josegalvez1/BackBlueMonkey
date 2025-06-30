package blueMonkey.user.domain.models;

import blueMonkey.booking.domain.models.Booking;
import blueMonkey.booking.infraestructure.controller.dtos.output.OutputBookingDto;
import blueMonkey.security.entity.RolesEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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

    @Column(unique = true, nullable = false)
    @Email
    @NotNull
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persona_roles", joinColumns = @JoinColumn(name = "persona_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
