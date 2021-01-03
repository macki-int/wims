package com.mj.wims.controller;

import com.mj.wims.model.Inventory;
import com.mj.wims.model.Reservation;
import com.mj.wims.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){

        return ResponseEntity.ok().body(reservationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if (reservationOptional.isPresent()){
            return ResponseEntity.ok().body(reservationOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inventories/{id}")
    public ResponseEntity<?> findAllReservationsByInventoriesId(@PathVariable Long id){

            return ResponseEntity.ok().body(reservationRepository.findAllReservationsByInventoriesId(id));
    }

    @PostMapping()
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {

        try {
            reservationRepository.save(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Object did not create");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if(reservationOptional.isPresent()){
            try {
                reservationRepository.delete(reservationOptional.get());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().body("Object did not delete");
        }

        return ResponseEntity.badRequest().build();
    }

}
