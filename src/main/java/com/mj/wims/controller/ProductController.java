package com.mj.wims.controller;

import com.mj.wims.converter.ProductDTOToProductConverter;
import com.mj.wims.dto.ProductDTO;
import com.mj.wims.model.Product;
import com.mj.wims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(productRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

    @GetMapping("/product-types/{id}")
    public ResponseEntity<?> findAllByProductTypeId(@PathVariable Long id){
        return ResponseEntity.ok().body(productRepository.findAllByProductTypeId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()){
            return ResponseEntity.ok().body(productOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        Optional<String> stringOptional = productRepository.findByName(productDTO.getName());

        if (stringOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Product product = ProductDTOToProductConverter.convert(productDTO);

        try {
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Object did not create");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        if(productRepository.existsById(product.getId())){
            try {
                productRepository.save(product);
                return ResponseEntity.ok().body(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setActive(true);
            productRepository.save(product);

            return ResponseEntity.ok().body(product);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setActive(false);
            productRepository.save(product);
            return ResponseEntity.ok().body(product);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()){
            try {
                productRepository.delete(productOptional.get());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().body("Object did not delete");
        }

        return ResponseEntity.badRequest().build();
    }
}
