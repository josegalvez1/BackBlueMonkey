package blueMonkey.product.application.service;

import blueMonkey.product.application.mapper.ProductMapper;
import blueMonkey.product.domain.models.ProductEntity;
import blueMonkey.product.infraestructure.repository.ProductRepository;
import blueMonkey.product.infraestructure.dtos.input.InputProductDto;
import blueMonkey.product.infraestructure.dtos.output.OutputProductDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public OutputProductDto addProduct(InputProductDto inputProductoDto) {
        ProductEntity product = productMapper.toEntity(inputProductoDto);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public OutputProductDto updateProduct(Long id, InputProductDto inputProductoDto) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));

        if(inputProductoDto.getName()!= null) product.setName(inputProductoDto.getName());
        if(inputProductoDto.getCategory()!= null) product.setCategory(inputProductoDto.getCategory());
        if(inputProductoDto.getDescription()!= null) product.setDescription(inputProductoDto.getDescription());
        if(inputProductoDto.getStock()!= 0 ) product.setStock(inputProductoDto.getStock());
        if(inputProductoDto.getPriceRegular()!= null) product.setPriceRegular(inputProductoDto.getPriceRegular());
        if(inputProductoDto.getImageUrl()!= null) product.setImageUrl(inputProductoDto.getImageUrl());
        if(inputProductoDto.getPrice()!= null) product.setPrice(inputProductoDto.getPrice());

        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public ResponseEntity<String> deleteProduct(Long id){
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));
        productRepository.delete(product);
        return ResponseEntity.status(200).body("Se ha borrado correctamente");
    }

    public List<OutputProductDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(producto -> productMapper.toDTO(producto))
                .collect(Collectors.toList());
    }

    public OutputProductDto getProduct(Long id){
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe producto con id: "+ id));
        return productMapper.toDTO(product);
    }
    public OutputProductDto getProductCategory(String category){
        ProductEntity product = productRepository.findByCategory(category);
        return productMapper.toDTO(product);
    }

}