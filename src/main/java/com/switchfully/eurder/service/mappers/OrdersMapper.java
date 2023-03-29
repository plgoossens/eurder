package com.switchfully.eurder.service.mappers;

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

    public Order toDomain(CreateOrderDTO createOrderDTO) {
        return new Order(createOrderDTO.getCustomerId(), itemsMapper.toDomain(createOrderDTO.getItems()));
    }

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getId(), order.getCustomerId(), order.calculateTotalPrice(), itemsMapper.toItemGroupDTO(order.getItems()));
    }
}
