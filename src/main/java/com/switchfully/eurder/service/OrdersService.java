package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.domain.repositories.OrdersRepository;
import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import com.switchfully.eurder.service.dto.*;
import com.switchfully.eurder.service.mappers.OrdersMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static com.switchfully.eurder.service.Utils.isUUIDValid;

@Service
public class OrdersService {

    private final OrdersMapper ordersMapper;
    private final OrdersRepository ordersRepository;
    private final CustomersRepository customersRepository;
    private final ItemsRepository itemsRepository;
    private final ItemsService itemsService;

    public OrdersService(OrdersMapper ordersMapper, OrdersRepository ordersRepository, CustomersRepository customersRepository, ItemsRepository itemsRepository, ItemsService itemsService) {
        this.ordersMapper = ordersMapper;
        this.ordersRepository = ordersRepository;
        this.customersRepository = customersRepository;
        this.itemsRepository = itemsRepository;
        this.itemsService = itemsService;
    }

    public OrderDTO createOrder(CreateOrderDTO createOrderDTO, UUID customerId) {
        validateCreateOrderDTO(createOrderDTO);
        Customer customer = customersRepository.getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " was not found."));
        Order order = ordersMapper.toDomain(createOrderDTO, customer);
        itemsService.updateStockForOrder(order);
        ordersRepository.add(order);
        return ordersMapper.toOrderDTO(order);
    }

    private void validateCreateOrderDTO(CreateOrderDTO createOrderDTO){
        if(createOrderDTO.getItems() == null || createOrderDTO.getItems().isEmpty()){
            throw new IllegalArgumentException("To create an order, the items list must not be empty");
        }
        for(CreateItemGroupDTO createItemGroupDTO : createOrderDTO.getItems()){
            if(!isUUIDValid(createItemGroupDTO.getItemId()) || itemsRepository.getById(createItemGroupDTO.getItemId()).isEmpty()){
                throw new ItemNotFoundException("Item with id " + createItemGroupDTO.getItemId() + " was not found.");
            }
            if(createItemGroupDTO.getAmount() < 1){
                throw new IllegalArgumentException("To create an order, the amount of ordered items must ge greater than zero for each item.");
            }
        }
    }

    public OrderReportDTO getOrdersReport(UUID customerId) {
        Collection<Order> orders = ordersRepository.getOrdersByCustomerId(customerId);
        return new OrderReportDTO(
                ordersMapper.toOrderDTO(orders),
                calculateTotalPriceOfOrders(orders));
    }

    public double calculateTotalPriceOfOrders(Collection<Order> orders){
        return orders.stream()
                .map(Order::calculateTotalPrice)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    public Collection<ItemGroupOrderDTO> getOrderItems(Optional<LocalDate> date) {
        return ordersRepository.getAllOrders().stream()
                .flatMap(order ->
                        ordersMapper.toItemGroupOrderDTO(
                                order.getItems(),
                                customersRepository.getById(order.getCustomerId())
                                        .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + order.getCustomerId() + " was not found"))
                                        .getAddress())
                                .stream())
                .filter(itemGroupOrderDTO -> itemGroupOrderDTO.getShippingDate().equals(date.orElse(itemGroupOrderDTO.getShippingDate())))
                .toList();
    }
}
