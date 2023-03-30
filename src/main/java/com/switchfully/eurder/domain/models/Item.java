package com.switchfully.eurder.domain.models;

import java.util.UUID;

public class Item {

    private final UUID id;
    private final String name;
    private final String description;
    private final double price;
    private int amount;

    public Item(String name, String description, double price, int amount) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public boolean enoughInStock(int neededAmount) {
        return amount-neededAmount >= 0;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
