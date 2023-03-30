package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.models.ItemGroup;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.dto.ItemGroupDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

class ItemsMapperTest {

    private ItemsMapper itemsMapper;
    private ItemsRepository itemsRepository;

    @BeforeEach
    void setup(){
        itemsRepository = Mockito.mock(ItemsRepository.class);
        itemsMapper = new ItemsMapper(itemsRepository);
    }

    @Test
    void toDomain() {
        // Given
        CreateItemDTO input = getDummyCreateItemDTO();
        Item expected = getDummyItem();

        // When
        Item output = itemsMapper.toDomain(input);

        // Then
        assertThat(output)
                .matches(result -> result.getName().equals(expected.getName()))
                .matches(result -> result.getDescription().equals(expected.getDescription()))
                .matches(result -> result.getPrice() == expected.getPrice())
                .matches(result -> result.getAmount() == expected.getAmount());
    }

    @Test
    void toIdDTO() {
        // Given
        Item input = getDummyItem();

        // When
        IdDTO result = itemsMapper.toIdDTO(input);

        // Then
        assertThat(result.getId()).isEqualTo(input.getId());
    }

    @Test
    void toDomainItemGroup() {
        // Given
        CreateItemGroupDTO input = getDummyCreateItemGroupDTO();
        ItemGroup expected = getDummyItemGroup();
        Mockito.when(itemsRepository.getById(input.getItemId())).thenReturn(Optional.of(expected.getItem()));

        // When
        ItemGroup result = itemsMapper.toDomain(input);

        // Then
        assertThat(result)
                .isNotNull()
                .matches(itemGroup -> itemGroup.getName().equals(expected.getName()))
                .matches(itemGroup -> itemGroup.getDescription().equals(expected.getDescription()))
                .matches(itemGroup -> itemGroup.getUnitPrice() == expected.getUnitPrice())
                .matches(itemGroup -> itemGroup.calculateTotalPrice() == expected.calculateTotalPrice())
                .matches(itemGroup -> itemGroup.getAmount() == expected.getAmount())
                .matches(itemGroup -> itemGroup.getItem().equals(expected.getItem()))
                .matches(itemGroup -> itemGroup.getShippingDate().equals(expected.getShippingDate()));
    }

    @Test
    void toItemGroupDTO() {
        // Given
        ItemGroup input = getDummyItemGroup();
        ItemGroupDTO expected = getDummyItemGroupInStockDTO();

        // When
        ItemGroupDTO result = itemsMapper.toItemGroupDTO(input);
        // Then
        assertThat(result)
                .isNotNull()
                .matches(itemGroupDTO -> itemGroupDTO.getItemName().equals(expected.getItemName()))
                .matches(itemGroupDTO -> itemGroupDTO.getItemDescription().equals(expected.getItemDescription()))
                .matches(itemGroupDTO -> itemGroupDTO.getShippingDate().equals(expected.getShippingDate()))
                .matches(itemGroupDTO -> itemGroupDTO.getUnitPrice() == expected.getUnitPrice())
                .matches(itemGroupDTO -> itemGroupDTO.getOrderedAmount() == expected.getOrderedAmount());
    }

    @Test
    void toItemGroupNotInStockDTO() {
        // Given
        ItemGroup input = getDummyItemGroupNotInStock();
        ItemGroupDTO expected = getDummyItemGroupNotInStockDTO();

        // When
        ItemGroupDTO result = itemsMapper.toItemGroupDTO(input);
        // Then
        assertThat(result)
                .isNotNull()
                .matches(itemGroupDTO -> itemGroupDTO.getItemName().equals(expected.getItemName()))
                .matches(itemGroupDTO -> itemGroupDTO.getItemDescription().equals(expected.getItemDescription()))
                .matches(itemGroupDTO -> itemGroupDTO.getShippingDate().equals(expected.getShippingDate()))
                .matches(itemGroupDTO -> itemGroupDTO.getUnitPrice() == expected.getUnitPrice())
                .matches(itemGroupDTO -> itemGroupDTO.getOrderedAmount() == expected.getOrderedAmount());
    }
}