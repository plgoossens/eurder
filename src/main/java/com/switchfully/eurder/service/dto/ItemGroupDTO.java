package com.switchfully.eurder.service.dto;

import java.time.LocalDate;

public class ItemGroupDTO {

    private final String itemId;
    private final String itemName;
    private final String itemDescription;
    private final double unitPrice;
    private final int amount;
    private final LocalDate shippingDate;

    public ItemGroupDTO(String itemId, String itemName, String itemDescription, double unitPrice, int amount, LocalDate shippingDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}
