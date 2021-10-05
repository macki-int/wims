package com.mj.wims.dto;

import com.mj.wims.model.Inventory;
import com.mj.wims.model.Product;

public class InventoryWithReservationCounterAndDeliveryCounterDTO {
    private Inventory inventory;
    private Long reservationCounter;
    private Long deliveryCounter;

    public InventoryWithReservationCounterAndDeliveryCounterDTO() {
    }

    public InventoryWithReservationCounterAndDeliveryCounterDTO(Inventory inventory, Long reservationCounter, Long deliveryCounter) {
        this.inventory = inventory;
        this.reservationCounter = reservationCounter;
        this.deliveryCounter = deliveryCounter;
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

    public Long getDeliveryCounter() {
        return deliveryCounter;
    }

    public void setDeliveryCounter(Long deliveryCounter) {
        this.deliveryCounter = deliveryCounter;
    }
}
