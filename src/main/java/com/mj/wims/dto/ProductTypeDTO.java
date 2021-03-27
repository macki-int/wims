package com.mj.wims.dto;

public class ProductTypeDTO {
    private String name;
    private Boolean calculate;

    public ProductTypeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCalculate() {
        return calculate;
    }

    public void setCalculate(Boolean calculate) {
        this.calculate = calculate;
    }
}
