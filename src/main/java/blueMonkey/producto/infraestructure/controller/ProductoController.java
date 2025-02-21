package blueMonkey.producto.infraestructure.controller;

import blueMonkey.producto.application.service.ProductoService;
import blueMonkey.producto.shared.dtos.input.InputProductoDto;
import blueMonkey.producto.shared.dtos.output.OutputProductoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @PutMapping
    public OutputProductoDto addProduct(InputProductoDto inputProductoDto){
        return productoService.addProduct(inputProductoDto);
    }

    @GetMapping
    public List<OutputProductoDto> getAllProducts(){
        return productoService.getAllProducts();
    }

    @GetMapping("/{id}")
    public OutputProductoDto getProduct(@PathVariable Long id){
        return productoService.getProduct(id);
    }

    @PutMapping("/{id}")
    public OutputProductoDto updateProduct(@PathVariable Long id, @Valid InputProductoDto inputProductoDto){
        return productoService.updateProduct(id,inputProductoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return productoService.deleteProduct(id);
    }

}
