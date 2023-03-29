package com.switchfully.eurder.service.dto;

import java.util.List;

public class OrderDTO {

    private final String id;
    private final String customerId;
    private final double totalPrice;
    private final List<ItemGroupDTO> items;

    public OrderDTO(String id, String customerId, double totalPrice, List<ItemGroupDTO> items) {
        this.id = id;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<ItemGroupDTO> getItems() {
        return items;
    }
}
