package blueMonkey.user.application.mapper;

import blueMonkey.security.entity.RoleEnum;
import blueMonkey.security.entity.RolesEntity;
import blueMonkey.user.domain.models.UserEntity;
import blueMonkey.user.infraestructure.dtos.input.InputUserDto;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    UserEntity toEntity(InputUserDto inputUsuarioDto);

    @Mapping(target = "roles", ignore = true) // asumiendo que no quieres exportar roles
    OutputUserDto toDTO(UserEntity usuario);

    default Set<RolesEntity> map(List<String> roleNames) {
        if (roleNames == null) return Set.of();
        return roleNames.stream()
                .map(name -> RolesEntity.builder()
                        .roleEnum(RoleEnum.valueOf(name.toUpperCase()))
                        .build())
                .collect(Collectors.toSet());
    }
}
