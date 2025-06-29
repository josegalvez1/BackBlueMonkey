package blueMonkey.tattoo.application.service;

import blueMonkey.tattoo.application.mapper.TattooMapper;
import blueMonkey.tattoo.infraestructure.dtos.input.InputTattooDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTattooDto;
import blueMonkey.tattoo.infraestructure.repository.TattooRepository;
import blueMonkey.tattoo.models.TattooEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TattooServiceImpl implements TattooService {

    @Autowired
    private TattooRepository tatuajeRepository;

    @Autowired
    private TattooMapper tatuajeMapper;

    public OutputTattooDto addTatuaje(InputTattooDto inputTatuajeDto) {
        TattooEntity product = tatuajeMapper.toEntity(inputTatuajeDto);
        tatuajeRepository.save(product);
        return tatuajeMapper.toDTO(product);
    }

    public OutputTattooDto updateTatuaje(Long id, InputTattooDto inputTatuajeDto) {
        TattooEntity tatuaje = tatuajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe tatuaje con id: "+ id));
        if(inputTatuajeDto.getName()!= null) tatuaje.setName(inputTatuajeDto.getName());
        if(inputTatuajeDto.getCategory()!= null) tatuaje.setCategory(inputTatuajeDto.getCategory());
        if(inputTatuajeDto.getSize()!= null) tatuaje.setSize(inputTatuajeDto.getSize());
        if(inputTatuajeDto.getBodyArea()!= null) tatuaje.setBodyArea(inputTatuajeDto.getBodyArea());

        if(inputTatuajeDto.getImageUrl()!= null) tatuaje.setImageUrl(inputTatuajeDto.getImageUrl());
        tatuajeRepository.save(tatuaje);
        return tatuajeMapper.toDTO(tatuaje);
    }

    public ResponseEntity<String> deleteTatuaje(Long id){
        TattooEntity producto = tatuajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe tatuaje con id: "+ id));
        tatuajeRepository.delete(producto);
        return ResponseEntity.status(200).body("Se ha borrado correctamente");
    }

    public List<OutputTattooDto> getAllTatuajes(){
        return tatuajeRepository.findAll().stream()
                .map(producto -> tatuajeMapper.toDTO(producto))
                .toList();
    }

    public OutputTattooDto getTatuaje(Long id){
        TattooEntity producto = tatuajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe tatuaje con id: "+ id));
        return tatuajeMapper.toDTO(producto);
    }

    public List<OutputTattooDto> filtrarTatuajes(String name, String category, String bodyArea, String size) {

        List<TattooEntity> tatuajes = tatuajeRepository.findByFilters(name, category, bodyArea ,size);
        return tatuajes.stream()
                .map(tatuajeMapper::toDTO).toList();
    }

}