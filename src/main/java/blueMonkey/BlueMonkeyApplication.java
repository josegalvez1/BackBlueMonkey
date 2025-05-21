package blueMonkey;

import blueMonkey.security.entity.RoleEnum;
import blueMonkey.security.entity.RolesEntity;
import blueMonkey.security.repository.RoleRepository;
import blueMonkey.user.domain.models.UserEntity;
import blueMonkey.user.infraestructure.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BlueMonkeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlueMonkeyApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		System.out.println(">>> Entrando en CommandLineRunner");

		return args -> {
			try {
				if (userRepository.count() == 0) {
					System.out.println(">>> No hay usuarios, insertando...");

					// Crear roles solo si no existen
					if (roleRepository.count() == 0) {
						RolesEntity roleAdmin = RolesEntity.builder()
								.roleEnum(RoleEnum.ADMIN)
								.build();

						RolesEntity roleUser = RolesEntity.builder()
								.roleEnum(RoleEnum.USER)
								.build();

						RolesEntity roleInvited = RolesEntity.builder()
								.roleEnum(RoleEnum.INVITED)
								.build();

						roleRepository.saveAll(List.of(roleAdmin, roleUser, roleInvited));
					}

					// Recuperar roles ya persistidos (entidades gestionadas)
					RolesEntity persistedAdmin = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
							.orElseThrow(() -> new IllegalStateException("Role ADMIN no encontrado"));

					RolesEntity persistedUser = roleRepository.findByRoleEnum(RoleEnum.USER)
							.orElseThrow(() -> new IllegalStateException("Role USER no encontrado"));

					// Crear usuarios
					UserEntity userJose = UserEntity.builder()
							.name("jose galvez")
							.email("jose.galvez@nter.es")
							.password(passwordEncoder.encode("Joselito.99"))
							.roles(Set.of(persistedAdmin))
							.build();

					UserEntity userLucia = UserEntity.builder()
							.name("lucia gg")
							.email("lucia.galvez@nter.es")
							.password(passwordEncoder.encode("lucia.99"))
							.roles(Set.of(persistedUser))
							.build();

					userRepository.saveAll(List.of(userJose, userLucia));
					System.out.println(">>> Usuarios insertados correctamente.");
				} else {
					System.out.println(">>> Ya hay usuarios, no se insertará nada.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
}
