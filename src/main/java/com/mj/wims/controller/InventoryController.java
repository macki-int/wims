package com.mj.wims.controller;

import com.mj.wims.WimsApplication;
import com.mj.wims.model.Inventory;
import com.mj.wims.repository.InventoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/inventories")
public class InventoryController {
    private static final Logger LOGGER = LogManager.getLogger(WimsApplication.class);
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Get all inventories");
        return ResponseEntity.ok().body(inventoryRepository.findAll());
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/quantities")
    public ResponseEntity<?> findAllWithQuantity() {

        return ResponseEntity.ok().body(inventoryRepository.findAllWithQuantity());
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);

        if (inventoryOptional.isPresent()) {
            return ResponseEntity.ok().body(inventoryOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findAllByProductId(@PathVariable Long id) {

        return ResponseEntity.ok().body(inventoryRepository.findAllByProductId(id));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<?> createInventory(@RequestBody Inventory inventory) {

        try {
            inventoryRepository.save(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Object did not create");
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping()
    public ResponseEntity<?> updateInventory(@RequestBody Inventory inventory) {
        if(inventoryRepository.existsById(inventory.getId())){
            try {
                inventoryRepository.save(inventory);
                return ResponseEntity.ok().body(inventory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/{id}")
    public ResponseEntity<?> setQuantity(@PathVariable Long id, @RequestBody Integer quantity) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);

        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            inventory.setQuantity(quantity);
            inventoryRepository.save(inventory);

            return ResponseEntity.ok().body(inventory);
        }

        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);

        if (inventoryOptional.isPresent()) {
            try {
                inventoryRepository.delete(inventoryOptional.get());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().body("Object did not delete");
        }

        return ResponseEntity.badRequest().build();
    }
}
