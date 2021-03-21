package com.mj.wims.controller;

import com.mj.wims.dto.ProductTypeDTO;
import com.mj.wims.model.ProductType;
import com.mj.wims.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product-types")
public class ProductTypeController {
    private ProductTypeRepository productTypeRepository;

    @Autowired
    ProductTypeController(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok().body(productTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<ProductType> productTypeOptional = productTypeRepository.findById(id);

        if (productTypeOptional.isPresent()){
            return ResponseEntity.ok().body(productTypeOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<?> createProductType(@RequestBody ProductTypeDTO productTypeDTO) {
        if (productTypeDTO.getName().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        ProductType productType = new ProductType();
        productType.setName(productTypeDTO.getName());

        try {
            productTypeRepository.save(productType);
            return ResponseEntity.status(HttpStatus.CREATED).body(productType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Object did not create");
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping()
    public ResponseEntity<?> updateProductType(@RequestBody ProductType productType){
        if (productType.getName().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        if(productTypeRepository.existsById(productType.getId())){
            try {
                productTypeRepository.save(productType);
                return ResponseEntity.ok().body(productType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable Long id){
        Optional<ProductType> productTypeOptional = productTypeRepository.findById(id);

        if (productTypeOptional.isPresent()){
            try {
                productTypeRepository.delete(productTypeOptional.get());
                return ResponseEntity.ok().body(productTypeOptional);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().body("Object did not delete");
        }

        return ResponseEntity.badRequest().build();
    }
}
