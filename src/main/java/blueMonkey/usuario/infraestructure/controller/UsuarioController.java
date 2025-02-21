package blueMonkey.usuario.infraestructure.controller;


import blueMonkey.usuario.application.service.UsuarioService;
import blueMonkey.usuario.shared.dtos.input.InputUsuarioDto;
import blueMonkey.usuario.shared.dtos.output.OutputUsuarioDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PutMapping
    public OutputUsuarioDto addUser(@Valid InputUsuarioDto inputUsuarioDto){
        return usuarioService.addUser(inputUsuarioDto);
    }

    @GetMapping
    public List<OutputUsuarioDto> getAllUsers(){
        return usuarioService.getAllUsers();
    }

    @GetMapping("/{id}")
    public OutputUsuarioDto getUser(@PathVariable Long id){
        return usuarioService.getUser(id);
    }

    @PutMapping("/{id}")
    public OutputUsuarioDto updateUser(@PathVariable Long id, @Valid InputUsuarioDto inputUsuarioDto){
        return usuarioService.updateUser(id,inputUsuarioDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return usuarioService.deleteUser(id);
    }

}
