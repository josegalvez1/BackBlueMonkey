package blueMonkey.tattoo.infraestructure.controller;

import blueMonkey.tattoo.application.service.TatuajeServiceImpl;
import blueMonkey.tattoo.infraestructure.dtos.input.InputTatuajeDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTatuajeDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tattoo")
public class TatuajeController {

    @Autowired
    TatuajeServiceImpl tatuajeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OutputTatuajeDto addTatuaje(@Valid @RequestBody InputTatuajeDto inputTatuajeDto){
        return tatuajeService.addTatuaje(inputTatuajeDto);
    }

    @GetMapping
    public List<OutputTatuajeDto> getAllTatuaje(){
        return tatuajeService.getAllTatuajes();
    }

    @GetMapping("/{id}")
    public OutputTatuajeDto getTatuaje(@PathVariable Long id){
        return tatuajeService.getTatuaje(id);
    }

    @GetMapping("/name/{name}")
    public OutputTatuajeDto getTatuajeByName(@PathVariable String name){
        return tatuajeService.getTatuajeName(name);
    }

    @GetMapping("/size/{size}")
    public OutputTatuajeDto getTatuajeBySize(@PathVariable String size){
        return tatuajeService.getTatuajeSize(size);
    }

    @GetMapping("/category/{category}")
    public OutputTatuajeDto getTatuajeByCategory(@PathVariable String category){
        return tatuajeService.getTatuajeCategory(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OutputTatuajeDto updateTatuaje(@PathVariable Long id, @Valid @RequestBody InputTatuajeDto inputTatuajeDto){
        return tatuajeService.updateTatuaje(id,inputTatuajeDto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> deleteTatuaje(@PathVariable Long id){
        return tatuajeService.deleteTatuaje(id);
    }

}
