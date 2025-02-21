package blueMonkey.usuario.application.service;

import blueMonkey.producto.application.mapper.ProductoMapper;
import blueMonkey.producto.domain.models.Producto;
import blueMonkey.producto.infraestructure.repository.ProductRepository;
import blueMonkey.producto.shared.dtos.input.InputProductoDto;
import blueMonkey.producto.shared.dtos.output.OutputProductoDto;
import blueMonkey.usuario.application.mapper.UsuarioMapper;
import blueMonkey.usuario.domain.models.Usuario;
import blueMonkey.usuario.infraestructure.repository.UsuarioRepository;
import blueMonkey.usuario.shared.dtos.input.InputUsuarioDto;
import blueMonkey.usuario.shared.dtos.output.OutputUsuarioDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public OutputUsuarioDto addUser(InputUsuarioDto inputUsuarioDto) {
        Usuario usuario = usuarioMapper.toEntity(inputUsuarioDto);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public OutputUsuarioDto updateUser(Long id, InputUsuarioDto inputUsuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe usuario con id: "+ id));
        if(inputUsuarioDto.getNombre()!= null) usuario.setNombre(inputUsuarioDto.getNombre());
        if(inputUsuarioDto.getEmail()!= null) usuario.setEmail(inputUsuarioDto.getEmail());
        if(inputUsuarioDto.getClave()!= null) usuario.setClave(inputUsuarioDto.getClave());
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public ResponseEntity<String> deleteUser(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));
        usuarioRepository.save(usuario);
        return ResponseEntity.status(200).body("Se ha borrado correctamente el usuario");
    }

    public List<OutputUsuarioDto> getAllUsers(){
        return usuarioRepository.findAll().stream()
                .map(usuario -> usuarioMapper.toDTO(usuario))
                .collect(Collectors.toList());
    }

    public OutputUsuarioDto getUser(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe usuario con id: "+ id));
        return usuarioMapper.toDTO(usuario);
    }

}
