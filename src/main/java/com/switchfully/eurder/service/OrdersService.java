package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.domain.repositories.OrdersRepository;
import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import com.switchfully.eurder.service.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import com.switchfully.eurder.service.mappers.OrdersMapper;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    private final OrdersMapper ordersMapper;
    private final OrdersRepository ordersRepository;
    private final CustomersRepository customersRepository;
    private final ItemsRepository itemsRepository;

    public OrdersService(OrdersMapper ordersMapper, OrdersRepository ordersRepository, CustomersRepository customersRepository, ItemsRepository itemsRepository) {
        this.ordersMapper = ordersMapper;
        this.ordersRepository = ordersRepository;
        this.customersRepository = customersRepository;
        this.itemsRepository = itemsRepository;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        validateCreateOrderDTO(createOrderDTO);
        Order order = ordersMapper.toDomain(createOrderDTO);
        ordersRepository.add(order);
        return ordersMapper.toOrderDTO(order);
    }

    private void validateCreateOrderDTO(CreateOrderDTO createOrderDTO){
        if(createOrderDTO.getCustomerId() == null || createOrderDTO.getItems() == null || createOrderDTO.getItems().isEmpty()){
            throw new IllegalArgumentException("To create an order, these fields need to be completed: customerId and items");
        }
        if(customersRepository.getById(createOrderDTO.getCustomerId()).isEmpty()){
            throw new CustomerNotFoundException("Customer with id " + createOrderDTO.getCustomerId() + " was not found.");
        }
        for(CreateItemGroupDTO createItemGroupDTO : createOrderDTO.getItems()){
            if(itemsRepository.getById(createItemGroupDTO.getItemId()).isEmpty()){
                throw new ItemNotFoundException("Item with id " + createItemGroupDTO.getItemId() + " was not found.");
            }
        }
    }
}
