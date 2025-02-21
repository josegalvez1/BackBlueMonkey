package blueMonkey.usuario.application.mapper;

import blueMonkey.usuario.domain.models.Usuario;
import blueMonkey.usuario.shared.dtos.input.InputUsuarioDto;
import blueMonkey.usuario.shared.dtos.output.OutputUsuarioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    OutputUsuarioDto toDTO(Usuario usuario);

    Usuario toEntity(InputUsuarioDto inputUsuarioDto);

}
