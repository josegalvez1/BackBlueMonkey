package blueMonkey.producto.infraestructure.repository;

import blueMonkey.producto.domain.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Long> {
}
