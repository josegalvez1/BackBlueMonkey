package blueMonkey.user.infraestructure.controller;


import blueMonkey.user.application.service.UserService;
import blueMonkey.user.infraestructure.dtos.input.InputUserDto;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @Transactional
    public OutputUserDto addUser(@Valid @RequestBody InputUserDto inputUsuarioDto){
        return userService.addUser(inputUsuarioDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OutputUserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OutputUserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public OutputUserDto updateUser(@PathVariable Long id, @Valid @RequestBody InputUserDto inputUsuarioDto){
        return userService.updateUser(id,inputUsuarioDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

}
