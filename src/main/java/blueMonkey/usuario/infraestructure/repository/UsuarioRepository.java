package blueMonkey.usuario.infraestructure.repository;

import blueMonkey.usuario.domain.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
