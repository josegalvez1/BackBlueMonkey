package blueMonkey.tattoo.application.service;

import blueMonkey.tattoo.infraestructure.dtos.input.InputTatuajeDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTatuajeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TatuajeService {

    OutputTatuajeDto addTatuaje(InputTatuajeDto inputTatuajeDto);
    OutputTatuajeDto updateTatuaje(Long id, InputTatuajeDto inputTatuajeDto);
    ResponseEntity<String> deleteTatuaje(Long id);
    List<OutputTatuajeDto> getAllTatuajes();
    OutputTatuajeDto getTatuaje(Long id);
    List<OutputTatuajeDto> filtrarTatuajes(String name, String category, String size);
}
