package blueMonkey.product.infraestructure.repository;

import blueMonkey.product.domain.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByCategory(String category);
}
