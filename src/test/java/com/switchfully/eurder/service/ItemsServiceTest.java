package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.mappers.ItemsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

class ItemsServiceTest {

    private static final String ITEM_NAME = "Item";
    private static final String ITEM_DESCRIPTION = "Description";
    private static final Double ITEM_PRICE = 9.99;
    private static final Integer ITEM_AMOUNT = 5;

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

    private Item getDummyItem(){
        return new Item(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT);
    }

    private CreateItemDTO getPartiallyNullDummyCreateItemDTO(){
        return new CreateItemDTO(ITEM_NAME, null, ITEM_PRICE, null);
    }

    private CreateItemDTO getDummyCreateItemDTOWithNegativeValues(){
        return new CreateItemDTO(ITEM_NAME, ITEM_DESCRIPTION, -ITEM_PRICE, ITEM_AMOUNT);
    }

    private CreateItemDTO getDummyCreateItemDTO(){
        return new CreateItemDTO(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT);
    }
}