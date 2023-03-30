package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Order;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OrdersRepository {

    private final Map<String, Order> ordersDB;

    public OrdersRepository() {
        this.ordersDB = new HashMap<>();
    }

    public void add(Order order) {
        ordersDB.put(order.getId(), order);
    }

    public Collection<Order> getOrdersByCustomerId(String customerId){
        return ordersDB.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .toList();
    }
}
