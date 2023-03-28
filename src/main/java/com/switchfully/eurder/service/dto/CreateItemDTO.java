package com.switchfully.eurder.service.dto;

public class CreateItemDTO {

    private final String name;
    private final String description;
    private final Double price;
    private final Integer amount;


    public CreateItemDTO(String name, String description, Double price, Integer amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }
}
