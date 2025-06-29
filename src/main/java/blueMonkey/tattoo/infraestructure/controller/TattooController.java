package blueMonkey.tattoo.infraestructure.controller;

import blueMonkey.tattoo.application.service.TattooServiceImpl;
import blueMonkey.tattoo.infraestructure.dtos.input.InputTattooDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTattooDto;
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
public class TattooController {

    @Autowired
    TattooServiceImpl tatuajeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OutputTattooDto addTatuaje(@Valid @RequestBody InputTattooDto inputTatuajeDto){
        return tatuajeService.addTatuaje(inputTatuajeDto);
    }

    @GetMapping
    public List<OutputTattooDto> getAllTatuaje(){
        return tatuajeService.getAllTatuajes();
    }

    @GetMapping("/{id}")
    public OutputTattooDto getTatuaje(@PathVariable Long id){
        return tatuajeService.getTatuaje(id);
    }


    @GetMapping("/tatuajes")
    public List<OutputTattooDto> filtrarTatuajes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String bodyArea,
            @RequestParam(required = false) String size
    ) {
return tatuajeService.filtrarTatuajes(name,category,bodyArea,size);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OutputTattooDto updateTatuaje(@PathVariable Long id, @Valid @RequestBody InputTattooDto inputTatuajeDto){
        return tatuajeService.updateTatuaje(id,inputTatuajeDto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> deleteTatuaje(@PathVariable Long id){
        return tatuajeService.deleteTatuaje(id);
    }

}
