package com.switchfully.eurder.service.dto;

import java.util.List;

public class CreateOrderDTO {

    private final String customerId;
    private final List<CreateItemGroupDTO> items;
    public CreateOrderDTO(String customerId, List<CreateItemGroupDTO> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<CreateItemGroupDTO> getItems() {
        return items;
    }
}
