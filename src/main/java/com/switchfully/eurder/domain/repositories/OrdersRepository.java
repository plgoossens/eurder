package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Order;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class OrdersRepository {

    private final Map<UUID, Order> ordersDB;

    public OrdersRepository() {
        this.ordersDB = new HashMap<>();
    }

    public void add(Order order) {
        ordersDB.put(order.getId(), order);
    }

    public Collection<Order> getOrdersByCustomerId(UUID customerId){
        return ordersDB.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .toList();
    }
}
