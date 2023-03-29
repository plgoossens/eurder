package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
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

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO, String customerId) {
        validateCreateOrderDTO(createOrderDTO);
        Customer customer = customersRepository.getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " was not found."));
        Order order = ordersMapper.toDomain(createOrderDTO, customer);
        ordersRepository.add(order);
        return ordersMapper.toOrderDTO(order);
    }

    private void validateCreateOrderDTO(CreateOrderDTO createOrderDTO){
        if(createOrderDTO.getItems() == null || createOrderDTO.getItems().isEmpty()){
            throw new IllegalArgumentException("To create an order, the items list must not be empty");
        }
        for(CreateItemGroupDTO createItemGroupDTO : createOrderDTO.getItems()){
            if(itemsRepository.getById(createItemGroupDTO.getItemId()).isEmpty()){
                throw new ItemNotFoundException("Item with id " + createItemGroupDTO.getItemId() + " was not found.");
            }
        }
    }
}
