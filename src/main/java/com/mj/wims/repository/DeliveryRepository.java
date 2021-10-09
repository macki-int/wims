package com.mj.wims.repository;

import com.mj.wims.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
    @Query("SELECT d FROM Delivery d JOIN d.inventory i WHERE d.dateOfDelivery > ?1 ORDER BY d.dateOfDelivery ASC")
    List<Object> findAllBeforeDeliveryDate(LocalDate date);

    @Query("SELECT d FROM Delivery d WHERE inventory_id = ?1 ORDER BY d.dateOfDelivery ASC")
    List<Object> findAllDeliveriesByInventoryId(Long inventoryId);
}
