package com.mj.wims.dto;

import com.mj.wims.model.Inventory;
import com.mj.wims.model.Product;

public class InventoryWithReservationCounterDTO {
    private Inventory inventory;
    private Long reservationCounter;

    public InventoryWithReservationCounterDTO() {
    }

    public InventoryWithReservationCounterDTO(Inventory inventory, Long reservationCounter) {
        this.inventory = inventory;
        this.reservationCounter = reservationCounter;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Long getReservationCounter() {
        return reservationCounter;
    }

    public void setReservationCounter(Long reservationCounter) {
        this.reservationCounter = reservationCounter;
    }
}
