package com.switchfully.eurder.service.dto;

import java.util.List;

public class CreateOrderDTO {

    private List<CreateItemGroupDTO> items;

    public CreateOrderDTO() {
    }

    public CreateOrderDTO(List<CreateItemGroupDTO> items) {
        this.items = items;
    }

    public List<CreateItemGroupDTO> getItems() {
        return items;
    }

    public void setItems(List<CreateItemGroupDTO> items) {
        this.items = items;
    }
}
