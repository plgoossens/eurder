package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.models.ItemGroup;
import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.ItemGroupOrderDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

class OrdersMapperTest {

    private OrdersMapper ordersMapper;
    private ItemsMapper itemsMapper;

    @BeforeEach
    void setup(){
        itemsMapper = Mockito.mock(ItemsMapper.class);
        ordersMapper = new OrdersMapper(itemsMapper);
    }

    @Test
    void toDomain() {
        // Given
        CreateOrderDTO input = getDummyCreateOrderDTO();
        Customer customer = getDummyCustomer();
        Order expected = getDummyOrder();

        Mockito.when(itemsMapper.toDomain(input.getItems())).thenReturn(List.of(getDummyItemGroup()));

        // When
        Order result = ordersMapper.toDomain(input, customer);

        // Then
        assertThat(result)
                .isNotNull()
                .matches(order -> order.calculateTotalPrice() == expected.calculateTotalPrice())
                .matches(order -> order.getItems().get(0).getName().equals(expected.getItems().get(0).getName()))
                .matches(order -> order.getItems().get(0).getDescription().equals(expected.getItems().get(0).getDescription()))
                .matches(order -> order.getItems().get(0).getShippingDate().equals(expected.getItems().get(0).getShippingDate()))
                .matches(order -> order.getItems().get(0).getUnitPrice() == expected.getItems().get(0).getUnitPrice())
                .matches(order -> order.getItems().get(0).getAmount() == expected.getItems().get(0).getAmount());
    }

    @Test
    void toOrderDTO() {
        // Given
        Order input = getDummyOrder();
        OrderDTO expected = getDummyOrderDTO();
        Mockito.when(itemsMapper.toItemGroupDTO(input.getItems())).thenReturn(List.of(getDummyItemGroupInStockDTO()));

        // When
        OrderDTO result = ordersMapper.toOrderDTO(input);

        // Then
        assertThat(result)
                .isNotNull()
                .matches(orderDTO -> orderDTO.getTotalPrice() == expected.getTotalPrice())
                .matches(orderDTO -> orderDTO.getItems().get(0).getItemName().equals(expected.getItems().get(0).getItemName()))
                .matches(orderDTO -> orderDTO.getItems().get(0).getItemDescription().equals(expected.getItems().get(0).getItemDescription()))
                .matches(orderDTO -> orderDTO.getItems().get(0).getShippingDate().equals(expected.getItems().get(0).getShippingDate()))
                .matches(orderDTO -> orderDTO.getItems().get(0).getUnitPrice() == expected.getItems().get(0).getUnitPrice())
                .matches(orderDTO -> orderDTO.getItems().get(0).getOrderedAmount() == expected.getItems().get(0).getOrderedAmount());
    }

    @Test
    void toItemGroupOrderDTO() {
        // Given
        ItemGroup itemGroup = getDummyItemGroup();
        String address = "address";

        // When
        ItemGroupOrderDTO result = ordersMapper.toItemGroupOrderDTO(itemGroup, address);

        // Then
        assertThat(result)
                .isNotNull()
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getItemId().equals(itemGroup.getItem().getId().toString()))
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getItemName().equals(itemGroup.getName()))
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getItemDescription().equals(itemGroup.getDescription()))
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getOrderedAmount() == itemGroup.getAmount())
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getTotalPrice() == itemGroup.calculateTotalPrice())
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getUnitPrice() == itemGroup.getUnitPrice())
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getShippingDate().equals(itemGroup.getShippingDate()))
                .matches(itemGroupOrderDTO -> itemGroupOrderDTO.getAddress().equals(address));
    }
}