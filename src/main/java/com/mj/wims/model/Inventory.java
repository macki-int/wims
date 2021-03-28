package com.mj.wims.model;


import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float productWidth;
    private Float productLength;
    private Integer quantity;
    private String description;
    private LocalDate updateDate;
    @NotNull
    @Value("${some.key:false}")
    private Boolean mainDimension;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Inventory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(Float productWidth) {
        this.productWidth = productWidth;
    }

    public Float getProductLength() {
        return productLength;
    }

    public void setProductLength(Float productLength) {
        this.productLength = productLength;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isMainDimension() {
        return mainDimension;
    }

    public void setMainDimension(boolean mainDimension) {
        this.mainDimension = mainDimension;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
