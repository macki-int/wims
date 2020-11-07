package com.mj.wims.repository;

import com.mj.wims.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository  extends JpaRepository<ProductType, Long> {

}
