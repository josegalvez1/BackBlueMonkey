package blueMonkey.user.application.mapper;

import blueMonkey.security.entity.RoleEnum;
import blueMonkey.security.entity.RolesEntity;
import blueMonkey.user.domain.models.UserEntity;
import blueMonkey.user.infraestructure.dtos.input.InputUserDto;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapStringsToRoles")
    UserEntity toEntity(InputUserDto inputUsuarioDto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToStrings")
    OutputUserDto toDTO(UserEntity usuario);

    // Convierte lista de RolesEntity a lista de Strings (nombres de roles)
    @Named("mapRolesToStrings")
    default List<String> mapRolesToStrings(Set<RolesEntity> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> role.getRoleEnum().name()) // Convertimos RoleEnum a String
                .collect(Collectors.toList());
    }

    // Convierte lista de Strings a lista de RolesEntity
    @Named("mapStringsToRoles")
    default Set<RolesEntity> mapStringsToRoles(List<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream().map(name -> {
            RolesEntity role = new RolesEntity();
            role.setRoleEnum(RoleEnum.valueOf(name)); // Convertimos String a RoleEnum
            return role;
        }).collect(Collectors.toSet());
    }
}
