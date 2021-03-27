package com.mj.wims.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "product_types")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique=true)
    private String name;
    private Boolean calculate;

    public ProductType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
