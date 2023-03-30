package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrdersMapper {

    private final ItemsMapper itemsMapper;

    public OrdersMapper(ItemsMapper itemsMapper) {
        this.itemsMapper = itemsMapper;
    }

    public Order toDomain(CreateOrderDTO createOrderDTO, Customer customer) {
        return new Order(customer.getId(), itemsMapper.toDomain(createOrderDTO.getItems()));
    }

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getId(), order.calculateTotalPrice(), itemsMapper.toItemGroupDTO(order.getItems()));
    }
}
