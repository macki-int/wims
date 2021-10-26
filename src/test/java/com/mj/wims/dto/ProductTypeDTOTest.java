package com.mj.wims.dto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductTypeDTOTest {

    @Test
    public void whenProductTypeDTOCreate(){
        //BEFORE
         ProductTypeDTO productTypeDTO = new ProductTypeDTO();

        //WHEN
        productTypeDTO.setName("ABC");
        productTypeDTO.setCalculate(true);

        //THEN
        assertEquals(productTypeDTO.getName(),"ABC");
        assertEquals(productTypeDTO.getCalculate(), true);
        assertTrue(productTypeDTO.getCalculate());
    }
}