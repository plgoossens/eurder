package com.switchfully.eurder.domain.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ItemGroup {

    public static final long SHIPPING_DAYS_IF_IN_STOCK = 1;
    public static final long SHIPPING_DAYS_IF_NOT_IN_STOCK = 7;

    private final Item item;
    private final String name;
    private final String description;
    private final double unitPrice;
    private final int amount;
    private final LocalDate shippingDate;

    public ItemGroup(Item item, int amount) {
        this.item = item;
        this.name = item.getName();
        this.description = item.getDescription();
        this.unitPrice = item.getPrice();
        this.amount = amount;
        this.shippingDate = calculateShippingDate();
    }

    private LocalDate calculateShippingDate(){
        if(item.enoughInStock(amount)){
            return LocalDate.now().plusDays(SHIPPING_DAYS_IF_IN_STOCK);
        }
        else{
            return LocalDate.now().plusDays(SHIPPING_DAYS_IF_NOT_IN_STOCK);
        }
    }

    public double calculateTotalPrice(){
        return unitPrice * amount;
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
