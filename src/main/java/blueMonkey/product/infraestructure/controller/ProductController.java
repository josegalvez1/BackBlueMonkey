package blueMonkey.product.infraestructure.controller;

import blueMonkey.product.application.service.ProductoService;
import blueMonkey.product.infraestructure.dtos.input.InputProductDto;
import blueMonkey.product.infraestructure.dtos.output.OutputProductDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductoService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public OutputProductDto addProduct(@Valid @RequestBody InputProductDto inputProductDto){
        return productService.addProduct(inputProductDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public List<OutputProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public OutputProductDto getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OutputProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody InputProductDto inputProductDto){
        return productService.updateProduct(id,inputProductDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public OutputProductDto getProduct(@PathVariable String category){
        return productService.getProductCategory(category);
    }

}
