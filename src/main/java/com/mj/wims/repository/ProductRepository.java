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


//    @Query(value = "SELECT product.id, product.product_type_id, product.name," +
//            "inventory.product_id, inventory.product_length, inventory.product_width, inventory.quantity, " +
//            "inventory.product_length * inventory.product_width * inventory.quantity AS area " +
//            "FROM product LEFT JOIN inventory ON product.id = inventory.product_id " +
//            "WHERE  product_type_id = ?1", nativeQuery = true)


    //withInactiveValue == true and withZeroValue == true
    @Query("SELECT i FROM Inventory i JOIN i.product p JOIN i.reservation r WHERE product_type_id = ?1 ORDER BY p.id ASC")
    List<Object> findAllProductsAndZeroQuantityByProductTypeId(Long productTypeId);

    //withInactiveValue == false and withZeroValue == true
    @Query("SELECT i FROM Inventory i JOIN i.product p WHERE product_type_id = ?1  AND p.active = true ORDER BY p.id ASC")
    List<Object> findActiveProductsAndZeroQuantityByProductTypeId(Long productTypeId);

    //withInactiveValue == true and withZeroValue == false
    @Query("SELECT i FROM Inventory i JOIN i.product p WHERE product_type_id = ?1 AND p.active = true AND i.quantity > 0 ORDER BY p.id ASC")
    List<Object> findActiveProductsAndNotZeroQuantityByProductTypeId(Long productTypeId);

    //withInactiveValue == false and withZeroValue == false
    @Query("SELECT i FROM Inventory i JOIN i.product p WHERE product_type_id = ?1 AND i.quantity > 0 ORDER BY p.id ASC")
    List<Object> findAllProductsAndNotZeroQuantityByProductTypeId(Long productTypeId);

//    @Query("SELECT p FROM Product p WHERE product_type_id = ?1 ORDER BY p.id ASC")


    @Query("SELECT MAX(i.updateDate) FROM Inventory i JOIN i.product p WHERE product_type_id = ?1")
    List<Object> findMaxUpdateDateByProductType(Long productTypeId);
}
