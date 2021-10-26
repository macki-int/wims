package com.mj.wims.dto;

import com.mj.wims.model.ProductType;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductDTOTest {

    @Test
    public void whenProductDTOCreate(){
        //BEFORE
        ProductDTO productDTO = new ProductDTO();
        ProductType productType = new ProductType();

        //WHEN
        productType.setId(1L);
        productType.setName("productTypeName");
        productType.setCalculate(true);

        productDTO.setName("ABC");
        productDTO.setDescription("description");
        productDTO.setProductType(productType);

        //THEN
        assertEquals(productDTO.getName(),"ABC");
        assertEquals(productDTO.getDescription(),"description");
        assertEquals(productDTO.getProductType(),productType);
    }
}