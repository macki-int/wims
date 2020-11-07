package com.mj.wims.dto;


import com.mj.wims.model.ProductType;

public class ProductDTO {
    private String name;
    private ProductType productType;

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
