package com.mj.wims.controller;

import com.mj.wims.model.Delivery;
import com.mj.wims.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    public DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryController(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(deliveryRepository.findAll());
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/valid-date")
    public ResponseEntity<?> getAllBeforeDeliverDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);

        return ResponseEntity.ok().body(deliveryRepository.findAllBeforeDeliveryDate(localDate));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/inventories/{id}")
    public ResponseEntity<?> findAllDeliveriesByInventoryId(@PathVariable Long id) {
        return ResponseEntity.ok().body(deliveryRepository.findAllDeliveriesByInventoryId(id));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<?> createDelivery(@RequestBody Delivery delivery) {

        try {
            deliveryRepository.save(delivery);
            return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Object did not create");
    }


}
