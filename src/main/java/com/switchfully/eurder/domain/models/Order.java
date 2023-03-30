package com.switchfully.eurder.domain.models;

import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final UUID customerId;
    private final List<ItemGroup> items;

    public Order(UUID customerId, List<ItemGroup> items) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = items;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
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
