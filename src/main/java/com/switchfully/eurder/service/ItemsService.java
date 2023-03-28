package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.mappers.ItemsMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {

    private final ItemsMapper itemsMapper;
    private final ItemsRepository itemsRepository;

    public ItemsService(ItemsMapper itemsMapper, ItemsRepository itemsRepository) {
        this.itemsMapper = itemsMapper;
        this.itemsRepository = itemsRepository;
    }

    public IdDTO addItem(CreateItemDTO createItemDTO) {
        validateCreateItemDTO(createItemDTO);
        Item item = itemsMapper.toDomain(createItemDTO);
        itemsRepository.add(item);
        return itemsMapper.toIdDTO(item);
    }

    private void validateCreateItemDTO(CreateItemDTO createItemDTO){
        if(createItemDTO.getName() == null ||
            createItemDTO.getDescription() == null ||
            createItemDTO.getPrice() == null ||
            createItemDTO.getAmount() == null){
            throw new IllegalArgumentException("To add an item, these fields need to be completed : name, description, price and amount.");
        }
        if(createItemDTO.getPrice() < 0.0 || createItemDTO.getAmount() < 0){
            throw new IllegalArgumentException("To add an item, the price and the amount must be positive.");
        }
    }
}
