package com.mj.wims.repository;

import com.mj.wims.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<String> findByName(String name);

    @Query(value = "SELECT product.name, product.id, inventory.product_id FROM product INNER JOIN inventory ON product.id = inventory.product_id WHERE  product_type_id = ?1", nativeQuery = true)

//    @Query("SELECT p FROM Products p WHERE p.product_type_id = ?")
//    @Query("SELECT p, i FROM Product p, Inventory i INNER JOIN p ON p.id = i.product_id WHERE p.product_type_id = :productTypeId")
//    @Query("SELECT p, i FROM Product p JOIN p.Inventory i WHERE p.product_type_id = ?1")
    List<Object> findAllByProductTypeId(Long productTypeId);
}
