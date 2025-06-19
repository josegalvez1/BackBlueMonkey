package blueMonkey.tattoo.application.service;

import blueMonkey.tattoo.application.mapper.TatuajeMapper;
import blueMonkey.tattoo.infraestructure.dtos.input.InputTatuajeDto;
import blueMonkey.tattoo.infraestructure.dtos.output.OutputTatuajeDto;
import blueMonkey.tattoo.infraestructure.repository.TatuajeRepository;
import blueMonkey.tattoo.models.TatuajeEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TatuajeServiceImpl implements TatuajeService {

    @Autowired
    private TatuajeRepository tatuajeRepository;

    @Autowired
    private TatuajeMapper tatuajeMapper;

    public OutputTatuajeDto addTatuaje(InputTatuajeDto inputTatuajeDto) {
        TatuajeEntity product = tatuajeMapper.toEntity(inputTatuajeDto);
        tatuajeRepository.save(product);
        return tatuajeMapper.toDTO(product);
    }

    public OutputTatuajeDto updateTatuaje(Long id, InputTatuajeDto inputTatuajeDto) {
        TatuajeEntity tatuaje = tatuajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe tatuaje con id: "+ id));
        if(inputTatuajeDto.getName()!= null) tatuaje.setName(inputTatuajeDto.getName());
        if(inputTatuajeDto.getCategory()!= null) tatuaje.setCategory(inputTatuajeDto.getCategory());
        if(inputTatuajeDto.getSize()!= null) tatuaje.setSize(inputTatuajeDto.getSize());
        if(inputTatuajeDto.getImageUrl()!= null) tatuaje.setImageUrl(inputTatuajeDto.getImageUrl());
        tatuajeRepository.save(tatuaje);
        return tatuajeMapper.toDTO(tatuaje);
    }

    public ResponseEntity<String> deleteTatuaje(Long id){
        TatuajeEntity producto = tatuajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe tatuaje con id: "+ id));
        tatuajeRepository.delete(producto);
        return ResponseEntity.status(200).body("Se ha borrado correctamente");
    }

    public List<OutputTatuajeDto> getAllTatuajes(){
        return tatuajeRepository.findAll().stream()
                .map(producto -> tatuajeMapper.toDTO(producto))
                .toList();
    }

    public OutputTatuajeDto getTatuaje(Long id){
        TatuajeEntity producto = tatuajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe tatuaje con id: "+ id));
        return tatuajeMapper.toDTO(producto);
    }

    public List<OutputTatuajeDto> filtrarTatuajes(String name, String category, String size) {

        List<TatuajeEntity> tatuajes = tatuajeRepository.findByFilters(name, category, size);
        return tatuajes.stream()
                .map(tatuajeMapper::toDTO).toList();
    }

}