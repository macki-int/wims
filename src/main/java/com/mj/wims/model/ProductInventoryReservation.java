package com.mj.wims.model;

public class ProductInventoryReservation {
    private Inventory inventory;
    private Product product;
    private Long reservationCounter;

    public ProductInventoryReservation() {
    }

    public ProductInventoryReservation(Inventory inventory, Product product, Long reservationCounter) {
        this.inventory = inventory;
        this.product = product;
        this.reservationCounter = reservationCounter;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getReservationCounter() {
        return reservationCounter;
    }

    public void setReservationCounter(Long reservationCounter) {
        this.reservationCounter = reservationCounter;
    }
}
