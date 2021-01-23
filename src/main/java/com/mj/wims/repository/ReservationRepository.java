package com.mj.wims.repository;


import com.mj.wims.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE inventory_id = ?1 ORDER BY u.username ASC")
    List<Object> findAllReservationsByInventoryId(Long inventoryId);

//    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE inventory_id = ?1 ORDER BY u.username ASC")
    List<Object> findAllByUserId(Long userId);
}
