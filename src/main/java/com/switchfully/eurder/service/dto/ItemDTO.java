package com.switchfully.eurder.service.dto;

import com.switchfully.eurder.domain.models.UrgencyLevel;

public class ItemDTO {
    private final String id;
    private final String name;
    private final String description;
    private final double price;
    private final int amount;
    private final UrgencyLevel urgencyLevel;

    public ItemDTO(String id, String name, String description, double price, int amount, UrgencyLevel urgencyLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.urgencyLevel = urgencyLevel;
    }

    public String getId() {
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

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }
}
