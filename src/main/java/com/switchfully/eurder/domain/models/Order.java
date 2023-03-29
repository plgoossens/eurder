package com.switchfully.eurder.domain.models;

import java.util.List;
import java.util.UUID;

public class Order {

    private final String id;
    private final String customerId;
    private final List<ItemGroup> items;

    public Order(String customerId, List<ItemGroup> items) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroup> getItems() {
        return items;
    }

    public double calculateTotalPrice(){
        return items.stream()
                .map(ItemGroup::calculateTotalPrice)
                .reduce(0.0, Double::sum);
    }
}
