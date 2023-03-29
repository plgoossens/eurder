package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Order;
import org.springframework.stereotype.Repository;

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
}
