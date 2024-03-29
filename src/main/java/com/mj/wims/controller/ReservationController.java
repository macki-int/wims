package com.mj.wims.controller;

import com.mj.wims.WimsApplication;
import com.mj.wims.model.Reservation;
import com.mj.wims.repository.ReservationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final Logger LOGGER = LogManager.getLogger(WimsApplication.class);
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Get all reservations");
        return ResponseEntity.ok().body(reservationRepository.findAll());
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/expire")
    public ResponseEntity<?> findAllWithExpiredDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        LOGGER.info("Got reservation with older expire date than current date: " + date);
        return ResponseEntity.ok().body(reservationRepository.findAllByDate(localDate));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if (reservationOptional.isPresent()) {
            LOGGER.info("Got reservation by id: " + id);
            return ResponseEntity.ok().body(reservationOptional);
        }
        LOGGER.info("No find reservation by id: " + id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long id) {
        LOGGER.info("Got reservations by User id: " + id);
        return ResponseEntity.ok().body(reservationRepository.findAllByUserId(id));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/inventories/{id}")
    public ResponseEntity<?> findAllReservationsByInventoryId(@PathVariable Long id) {
        LOGGER.info("Got reservations by Inventory id: " + id);
        return ResponseEntity.ok().body(reservationRepository.findAllReservationsByInventoryId(id));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping()
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {

        try {
            reservationRepository.save(reservation);
            LOGGER.info("Created reservation: " + reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("No created reservation: " + reservation);
        return ResponseEntity.badRequest().body("Object did not create");
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping()
    public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation) {
        if (reservationRepository.existsById(reservation.getId())) {
            try {
                reservationRepository.save(reservation);
                LOGGER.info("Updated reservation: " + reservation);
                return ResponseEntity.ok().body(reservation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("No updated reservation: " + reservation);
        return ResponseEntity.notFound().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if (reservationOptional.isPresent()) {
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
