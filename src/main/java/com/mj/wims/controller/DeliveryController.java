package com.mj.wims.controller;

import com.mj.wims.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

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
    

}
