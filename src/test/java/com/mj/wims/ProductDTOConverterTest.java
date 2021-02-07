package com.mj.wims;

import com.mj.wims.converter.ProductDTOToProductConverter;
import com.mj.wims.dto.ProductDTO;
import com.mj.wims.model.Product;
import com.mj.wims.model.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDTOConverterTest {
    private ProductDTO productDTO;
    private ProductType productType;

    @BeforeEach
    void init() {
        productType = new ProductType();
        productType.setId(1L);
        productType.setName("productType");

        productDTO = getSamplerProductDTO();
    }

    private ProductDTO getSamplerProductDTO() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName("product");
        productDTO.setProductType(productType);
        return productDTO;
    }

    @Test
    void whenConvertUserDTOtoUser() {
        //BEFORE
        Product product;

        //WHEN
        product = ProductDTOToProductConverter.convert(productDTO);

        //THEN
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(true, product.getActive());
    }
}
