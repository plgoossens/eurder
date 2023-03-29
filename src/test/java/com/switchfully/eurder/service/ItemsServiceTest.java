package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.mappers.ItemsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

public class ItemsServiceTest {

    private ItemsService itemsService;
    private ItemsRepository itemsRepository;
    private ItemsMapper itemsMapper;

    @BeforeEach
    void setup(){
        itemsRepository = Mockito.mock(ItemsRepository.class);
        itemsMapper = Mockito.mock(ItemsMapper.class);
        itemsService = new ItemsService(itemsMapper, itemsRepository);
    }

    @Test
    void addItem_whenCreateItemDTOPartiallyNull_thenThrowsIllegalArgumentException() {
        // Given
        CreateItemDTO createItemDTO = getPartiallyNullDummyCreateItemDTO();

        // When
        assertThatThrownBy(() -> {
            itemsService.addItem(createItemDTO);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To add an item, these fields need to be completed : name, description, price and amount.");

    }

    @Test
    void addItem_whenCreateItemDTOWithNegativeValues_thenThrowsIllegalArgumentException() {
        // Given
        CreateItemDTO createItemDTO = getDummyCreateItemDTOWithNegativeValues();

        // When
        assertThatThrownBy(() -> {
            itemsService.addItem(createItemDTO);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To add an item, the price and the amount must be positive.");

    }

    @Test
    void addItem() {
        // Given
        CreateItemDTO createItemDTO = getDummyCreateItemDTO();
        Item item = getDummyItem();

        Mockito.when(itemsMapper.toDomain(createItemDTO)).thenReturn(item);

        // When
        itemsService.addItem(createItemDTO);

        // Then
        Mockito.verify(itemsRepository).add(item);
    }
}