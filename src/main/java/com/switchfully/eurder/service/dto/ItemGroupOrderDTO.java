package com.switchfully.eurder.service.dto;

import java.time.LocalDate;

public class ItemGroupOrderDTO {
    private final String itemId;
    private final String itemName;
    private final String itemDescription;
    private final double unitPrice;
    private final int orderedAmount;
    private final double totalPrice;
    private final LocalDate shippingDate;
    private final String address;

    public ItemGroupOrderDTO(String itemId, String itemName, String itemDescription, double unitPrice, int orderedAmount, double totalPrice, LocalDate shippingDate, String address) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.orderedAmount = orderedAmount;
        this.totalPrice = totalPrice;
        this.shippingDate = shippingDate;
        this.address = address;
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

    public int getOrderedAmount() {
        return orderedAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public String getAddress() {
        return address;
    }
}
