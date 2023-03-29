package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.domain.repositories.OrdersRepository;
import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.mappers.OrdersMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

class OrderServiceTest {

    private OrdersMapper ordersMapper;
    private OrdersRepository ordersRepository;
    private CustomersRepository customersRepository;
    private ItemsRepository itemsRepository;
    private OrdersService ordersService;

    @BeforeEach
    void setup(){
        ordersMapper = Mockito.mock(OrdersMapper.class);
        ordersRepository = Mockito.mock(OrdersRepository.class);
        customersRepository = Mockito.mock(CustomersRepository.class);
        itemsRepository = Mockito.mock(ItemsRepository.class);
        ordersService = new OrdersService(ordersMapper, ordersRepository, customersRepository, itemsRepository);
    }

    @Test
    void createOrder() {
        // Given
        CreateOrderDTO input = getDummyCreateOrderDTO();
        Order order = getDummyOrder();
        Mockito.when(ordersMapper.toDomain(input)).thenReturn(order);
        Mockito.when(customersRepository.getById(input.getCustomerId())).thenReturn(Optional.of(getDummyCustomer()));
        Mockito.when(itemsRepository.getById(input.getItems().get(0).getItemId())).thenReturn(Optional.of(getDummyItem()));

        // When
        ordersService.createOrder(input);

        // Then
        Mockito.verify(ordersRepository).add(order);
    }

    @Test
    void createOrder_whenCreateOrderDTOIsPartiallyNull_thenThrowsIllegalArgumentException() {
        // Given
        CreateOrderDTO input = getPartiallyNullDummyCreateOrderDTO();

        // When
        assertThatThrownBy(() -> ordersService.createOrder(input)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To create an order, these fields need to be completed: customerId and items");
    }

    @Test
    void createOrder_whenCustomerDoesntExist_thenThrowsCustomerNotFoundException() {
        // Given
        CreateOrderDTO input = getDummyCreateOrderDTO();
        Mockito.when(customersRepository.getById(input.getCustomerId())).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> ordersService.createOrder(input)).isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with id " + input.getCustomerId() + " was not found.");
    }

    @Test
    void createOrder_whenItemDoesntExist_thenThrowsItemNotFoundException() {
        // Given
        CreateOrderDTO input = getDummyCreateOrderDTO();
        Mockito.when(customersRepository.getById(input.getCustomerId())).thenReturn(Optional.of(getDummyCustomer()));
        Mockito.when(itemsRepository.getById(input.getItems().get(0).getItemId())).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> ordersService.createOrder(input)).isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Item with id " + input.getItems().get(0).getItemId() + " was not found.");
    }
}