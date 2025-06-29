package blueMonkey.product.infraestructure.controller;

import blueMonkey.product.application.service.ProductService;
import blueMonkey.product.infraestructure.dtos.input.InputProductDto;
import blueMonkey.product.infraestructure.dtos.output.OutputProductDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OutputProductDto addProduct(@Valid @RequestBody InputProductDto inputProductDto){
        return productService.addProduct(inputProductDto);
    }

    @GetMapping
    public List<OutputProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public OutputProductDto getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OutputProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody InputProductDto inputProductDto){
        return productService.updateProduct(id,inputProductDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/category/{category}")
    public OutputProductDto getProduct(@PathVariable String category){
        return productService.getProductCategory(category);
    }

}
