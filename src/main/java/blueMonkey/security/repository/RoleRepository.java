package blueMonkey.security.repository;

import blueMonkey.security.entity.RoleEnum;
import blueMonkey.security.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, Long> {

    Optional<RolesEntity> findByRoleEnum(RoleEnum roleEnum);

}
