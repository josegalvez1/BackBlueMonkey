package blueMonkey.tattoo.infraestructure.repository;

import blueMonkey.tattoo.models.TatuajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TatuajeRepository extends JpaRepository<TatuajeEntity, Long> {
    TatuajeEntity findByName(String name);
    TatuajeEntity findByCategory(String category);
    TatuajeEntity findBySize(String size);
}
