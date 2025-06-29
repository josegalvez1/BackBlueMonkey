package blueMonkey.tattoo.infraestructure.repository;

import blueMonkey.tattoo.models.TatuajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TatuajeRepository extends JpaRepository<TatuajeEntity, Long> {
    TatuajeEntity findByName(String name);
    TatuajeEntity findByCategory(String category);
    TatuajeEntity findBySize(String size);
    TatuajeEntity findByImageUrl(String imageUrl);


    @Query("SELECT t FROM TatuajeEntity t WHERE " +
            "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR LOWER(t.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
            "(:bodyArea IS NULL OR LOWER(t.bodyArea) LIKE LOWER(CONCAT('%', :bodyArea, '%'))) AND "+
            "(:size IS NULL OR LOWER(t.size) LIKE LOWER(CONCAT('%', :size, '%')))"

    )
    List<TatuajeEntity> findByFilters(@Param("name") String name,
                                      @Param("category") String category,
                                      @Param("bodyArea") String bodyArea,
                                      @Param("size") String size);

}
