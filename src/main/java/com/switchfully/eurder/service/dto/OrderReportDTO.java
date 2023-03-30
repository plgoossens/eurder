package com.switchfully.eurder.service.dto;

import java.util.Collection;

public class OrderReportDTO {

    private final Collection<OrderDTO> orders;
    private final double totalPrice;

    public OrderReportDTO(Collection<OrderDTO> orders, double totalPrice) {
        this.orders = orders;
        this.totalPrice = totalPrice;
    }

    public Collection<OrderDTO> getOrders() {
        return orders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
