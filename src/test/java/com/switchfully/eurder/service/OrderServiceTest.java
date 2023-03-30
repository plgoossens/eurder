package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
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

import java.util.Collection;
import java.util.List;
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
        Customer customer = getDummyCustomer();
        Order order = getDummyOrder();
        Mockito.when(ordersMapper.toDomain(input, customer)).thenReturn(order);
        Mockito.when(customersRepository.getById(customer.getId())).thenReturn(Optional.of(customer));
        Mockito.when(itemsRepository.getById(input.getItems().get(0).getItemId())).thenReturn(Optional.of(getDummyItem()));

        // When
        ordersService.createOrder(input, customer.getId());

        // Then
        Mockito.verify(ordersRepository).add(order);
        Mockito.verify(itemsRepository).updateStockForOrder(order);
    }

    @Test
    void createOrder_whenCreateOrderDTOIsPartiallyNull_thenThrowsIllegalArgumentException() {
        // Given
        CreateOrderDTO input = getPartiallyNullDummyCreateOrderDTO();
        Customer customer = getDummyCustomer();

        // When
        assertThatThrownBy(() -> ordersService.createOrder(input, customer.getId())).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To create an order, the items list must not be empty");
    }

    @Test
    void createOrder_whenCustomerDoesntExist_thenThrowsCustomerNotFoundException() {
        // Given
        CreateOrderDTO input = getDummyCreateOrderDTO();
        Customer customer = getDummyCustomer();
        Mockito.when(customersRepository.getById(customer.getId())).thenReturn(Optional.empty());
        Mockito.when(itemsRepository.getById(input.getItems().get(0).getItemId())).thenReturn(Optional.of(getDummyItem()));

        // When
        assertThatThrownBy(() -> ordersService.createOrder(input, customer.getId())).isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with id " + customer.getId() + " was not found.");
    }

    @Test
    void createOrder_whenItemDoesntExist_thenThrowsItemNotFoundException() {
        // Given
        CreateOrderDTO input = getDummyCreateOrderDTO();
        Customer customer = getDummyCustomer();
        Mockito.when(customersRepository.getById(customer.getId())).thenReturn(Optional.of(customer));
        Mockito.when(itemsRepository.getById(input.getItems().get(0).getItemId())).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> ordersService.createOrder(input, customer.getId())).isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Item with id " + input.getItems().get(0).getItemId() + " was not found.");
    }

    @Test
    void calculateTotalPriceOfOrders() {
        // Given
        Collection<Order> input = List.of(getDummyOrder(), getDummyOrder());

        // When
        double result = ordersService.calculateTotalPriceOfOrders(input);

        // Then
        assertThat(result).isEqualTo(getDummyOrder().calculateTotalPrice()*2);
    }

    @Test
    void calculateTotalPriceOfOrders_whenListIsEmpty_thenReturnsZero() {
        // Given
        Collection<Order> input = List.of();

        // When
        double result = ordersService.calculateTotalPriceOfOrders(input);

        // Then
        assertThat(result).isEqualTo(0.0);
    }
}