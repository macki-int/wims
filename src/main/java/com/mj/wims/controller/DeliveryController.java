package com.mj.wims.controller;

import com.mj.wims.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(deliveryRepository.findAll());
    }

    @GetMapping("/valid-date")
    public ResponseEntity<?> getAllBeforeDeliverDate(@RequestParam String date){
        LocalDate localDate = LocalDate.parse(date);

        return ResponseEntity.ok().body(deliveryRepository.findAllBeforeDeliveryDate(localDate));
    }
    
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/dateOfDelivery/{id}")
    public ResponseEntity<?> findAllDeliveriesByInventoryId(@PathVariable Long id){
        return ResponseEntity.ok().body(deliveryRepository.findAllDeliveriesByInventoryId(id));
    }


}
