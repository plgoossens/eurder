package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.models.ItemGroup;
import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.ItemGroupOrderDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        return new OrderDTO(order.getId().toString(), order.calculateTotalPrice(), itemsMapper.toItemGroupDTO(order.getItems()));
    }

    public Collection<OrderDTO> toOrderDTO(Collection<Order> orders){
        return orders.stream()
                .map(this::toOrderDTO)
                .toList();
    }

    public ItemGroupOrderDTO toItemGroupOrderDTO(ItemGroup itemGroup, String address){
        return new ItemGroupOrderDTO(itemGroup.getItem().getId().toString(), itemGroup.getName(), itemGroup.getDescription(), itemGroup.getUnitPrice(), itemGroup.getAmount(), itemGroup.calculateTotalPrice(), itemGroup.getShippingDate(), address);
    }

    public Collection<ItemGroupOrderDTO> toItemGroupOrderDTO(Collection<ItemGroup> itemGroups, String address){
        return itemGroups.stream()
                .map(itemGroup -> toItemGroupOrderDTO(itemGroup, address))
                .toList();
    }
}
