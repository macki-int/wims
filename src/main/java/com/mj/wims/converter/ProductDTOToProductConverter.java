package com.mj.wims.converter;


import com.mj.wims.dto.ProductDTO;
import com.mj.wims.model.Product;

public class ProductDTOToProductConverter {

    public static Product convert(ProductDTO productDTO) {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setProductType(productDTO.getProductType());
        product.setActive(true);

        return product;
    }
}
