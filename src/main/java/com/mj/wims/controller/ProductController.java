package com.mj.wims.controller;

import com.mj.wims.converter.ProductDTOToProductConverter;
import com.mj.wims.dto.ProductDTO;
import com.mj.wims.model.Product;
import com.mj.wims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(productRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/product-types/{id}")
    public ResponseEntity<?> findProductsByProductTypeId(@PathVariable Long id,
                                                         @RequestParam (defaultValue = "false") boolean withZeroValue,
                                                         @RequestParam (defaultValue = "false") boolean withInactiveValue) {

        if( withInactiveValue == true && withZeroValue == true){
            return ResponseEntity.ok().body(productRepository.findAllProductsAndZeroQuantityByProductTypeId(id));
        } else if( withInactiveValue == false && withZeroValue == true){
            return ResponseEntity.ok().body(productRepository.findActiveProductsAndZeroQuantityByProductTypeId(id));
        } else if(withInactiveValue == true && withZeroValue == false){
            return ResponseEntity.ok().body(productRepository.findAllProductsAndNotZeroQuantityByProductTypeId(id));
        }

        //withZeroValue == false and withInactiveValue == false
        return ResponseEntity.ok().body(productRepository.findActiveProductsAndNotZeroQuantityByProductTypeId(id));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/product-types/max-update-date/{id}")
    public ResponseEntity<?> findMaxUpdateDateByProductType(@PathVariable Long id) {
        return ResponseEntity.ok().body(productRepository.findMaxUpdateDateByProductType(id));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
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

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (productRepository.existsById(product.getId())) {
            try {
                productRepository.save(product);
                return ResponseEntity.ok().body(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/act/{id}")
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

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/dea/{id}")
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

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
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
