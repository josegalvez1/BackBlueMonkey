package blueMonkey.producto.application.service;

import blueMonkey.producto.application.mapper.ProductoMapper;
import blueMonkey.producto.domain.models.Producto;
import blueMonkey.producto.infraestructure.repository.ProductRepository;
import blueMonkey.producto.shared.dtos.input.InputProductoDto;
import blueMonkey.producto.shared.dtos.output.OutputProductoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public OutputProductoDto addProduct(InputProductoDto inputProductoDto) {
        Producto producto = productoMapper.toEntity(inputProductoDto);
        productRepository.save(producto);
        return productoMapper.toDTO(producto);
    }

    public OutputProductoDto updateProduct(Long id, InputProductoDto inputProductoDto) {
        Producto producto = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));
        if(inputProductoDto.getNombre()!= null) producto.setNombre(inputProductoDto.getNombre());
        if(inputProductoDto.getCategoria()!= null) producto.setCategoria(inputProductoDto.getCategoria());
        if(inputProductoDto.getDescripcion()!= null) producto.setDescripcion(inputProductoDto.getDescripcion());
        if(inputProductoDto.getStock()!= 0 ) producto.setStock(inputProductoDto.getStock());
        if(inputProductoDto.getPrecio()!= null) producto.setNombre(inputProductoDto.getNombre());
        if(inputProductoDto.getPrecioRegular()!= null) producto.setImagenUrl(inputProductoDto.getImagenUrl());
        if(inputProductoDto.getImagenUrl()!= null) producto.setImagenUrl(inputProductoDto.getImagenUrl());
        productRepository.save(producto);
        return productoMapper.toDTO(producto);
    }

    public ResponseEntity<String> deleteProduct(Long id){
        Producto producto = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));
        productRepository.delete(producto);
        return ResponseEntity.status(200).body("Se ha borrado correctamente");
    }

    public List<OutputProductoDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(producto -> productoMapper.toDTO(producto))
                .collect(Collectors.toList());
    }

    public OutputProductoDto getProduct(Long id){
        Producto producto = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));
        return productoMapper.toDTO(producto);
    }

}