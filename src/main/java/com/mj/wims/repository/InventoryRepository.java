package com.mj.wims.repository;

import com.mj.wims.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.quantity > 0")
    List<Object> findAllWithQuantity();

    //    @Query("SELECT i FROM Inventory i WHERE i.product_id = ?1 ORDER BY p.id ASC")
    List<Object> findAllByProductId(Long productId);

//    @Query("SELECT i FROM Inventory i WHERE i.product_id = ?1 ORDER BY i.quantity ASC")
    List<Object> findAllByProductIdOrderByQuantityAsc(Long productId);
}
