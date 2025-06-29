package blueMonkey.tattoo.application.service;

import blueMonkey.tattoo.infraestructure.dtos.input.InputTattooDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTattooDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TattooService {

    OutputTattooDto addTatuaje(InputTattooDto inputTatuajeDto);
    OutputTattooDto updateTatuaje(Long id, InputTattooDto inputTatuajeDto);
    ResponseEntity<String> deleteTatuaje(Long id);
    List<OutputTattooDto> getAllTatuajes();
    OutputTattooDto getTatuaje(Long id);
    List<OutputTattooDto> filtrarTatuajes(String name, String category, String bodyArea, String size);
}
