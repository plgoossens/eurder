package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.models.UrgencyLevel;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.ItemDTO;
import com.switchfully.eurder.service.mappers.ItemsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        assertThatThrownBy(() -> itemsService.addItem(createItemDTO)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To add an item, these fields need to be completed : name, description, price and amount.");

    }

    @Test
    void addItem_whenCreateItemDTOWithNegativeValues_thenThrowsIllegalArgumentException() {
        // Given
        CreateItemDTO createItemDTO = getDummyCreateItemDTOWithNegativeValues();

        // When
        assertThatThrownBy(() -> itemsService.addItem(createItemDTO)).isInstanceOf(IllegalArgumentException.class)
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

    @Test
    void getItemOverview_withoutParam() {
        // Given
        Item itemStockHigh = getDummyItemStockHigh();
        Item itemStockMedium = getDummyItem();
        Item itemStockLow = getDummyItemStockLow();
        Mockito.when(itemsRepository.getAllItems()).thenReturn(List.of(itemStockHigh, itemStockMedium, itemStockLow));
        Mockito.when(itemsMapper.toItemDTO(itemStockHigh)).thenReturn(getDummyItemDTOStockHigh());
        Mockito.when(itemsMapper.toItemDTO(itemStockMedium)).thenReturn(getDummyItemDTO());
        Mockito.when(itemsMapper.toItemDTO(itemStockLow)).thenReturn(getDummyItemDTOStockLow());

        // When
        Collection<ItemDTO> result = itemsService.getItemOverview(Optional.empty());

        // Then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        assertThat(result.stream().toList())
                .matches(itemDTOS -> itemDTOS.get(0).getUrgencyLevel() == UrgencyLevel.STOCK_LOW)
                .matches(itemDTOS -> itemDTOS.get(1).getUrgencyLevel() == UrgencyLevel.STOCK_MEDIUM)
                .matches(itemDTOS -> itemDTOS.get(2).getUrgencyLevel() == UrgencyLevel.STOCK_HIGH);
    }

    @Test
    void getItemOverview_withParam() {
        // Given
        Item itemStockHigh = getDummyItemStockHigh();
        Item itemStockMedium = getDummyItem();
        Item itemStockLow = getDummyItemStockLow();
        Mockito.when(itemsRepository.getAllItems()).thenReturn(List.of(itemStockHigh, itemStockMedium, itemStockLow));
        Mockito.when(itemsMapper.toItemDTO(itemStockHigh)).thenReturn(getDummyItemDTOStockHigh());
        Mockito.when(itemsMapper.toItemDTO(itemStockMedium)).thenReturn(getDummyItemDTO());
        Mockito.when(itemsMapper.toItemDTO(itemStockLow)).thenReturn(getDummyItemDTOStockLow());

        // When
        Collection<ItemDTO> result = itemsService.getItemOverview(Optional.of(UrgencyLevel.STOCK_MEDIUM));

        // Then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(result.stream().toList())
                .matches(itemDTOS -> itemDTOS.get(0).getUrgencyLevel() == UrgencyLevel.STOCK_MEDIUM);
    }

    @Test
    void updateItem() {
        // Given
        Item item = getDummyItem();
        CreateItemDTO createItemDTO = new CreateItemDTO(null, null, 99.99, null);
        Mockito.when(itemsRepository.getById(ITEM_ID.toString())).thenReturn(Optional.of(item));

        // When
        itemsService.updateItem(createItemDTO, ITEM_ID.toString());

        // Then
        Mockito.verify(itemsRepository).getById(ITEM_ID.toString());
        Mockito.verify(itemsMapper).updateItemFromDTO(item, createItemDTO);
    }

    @Test
    void updateItem_withInvalidUUID() {
        assertThatThrownBy(() -> itemsService.updateItem(getDummyCreateItemDTO(), "invalidId"))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Item with id invalidId was not found.");
    }
}