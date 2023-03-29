package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.models.ItemGroup;
import com.switchfully.eurder.domain.repositories.ItemsRepository;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.dto.ItemGroupDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemsMapper {

    private final ItemsRepository itemsRepository;

    public ItemsMapper(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public Item toDomain(CreateItemDTO createItemDTO) {
        return new Item(createItemDTO.getName(), createItemDTO.getDescription(), createItemDTO.getPrice(), createItemDTO.getAmount());
    }

    public ItemGroup toDomain(CreateItemGroupDTO createItemGroupDTO){
        return new ItemGroup(
                itemsRepository.getById(createItemGroupDTO.getItemId())
                        .orElseThrow(() -> new ItemNotFoundException("Item with id " + createItemGroupDTO.getItemId() + " was not found")),
                createItemGroupDTO.getAmount());
    }

    public List<ItemGroup> toDomain(List<CreateItemGroupDTO> createItemGroupDTOList){
        return createItemGroupDTOList.stream()
                .map(this::toDomain)
                .toList();
    }

    public IdDTO toIdDTO(Item item) {
        return new IdDTO(item.getId());
    }

    public ItemGroupDTO toItemGroupDTO(ItemGroup itemGroup){
        return new ItemGroupDTO(itemGroup.getItem().getId(), itemGroup.getName(), itemGroup.getDescription(), itemGroup.getUnitPrice(), itemGroup.getAmount(), itemGroup.getShippingDate());
    }

    public List<ItemGroupDTO> toItemGroupDTO(List<ItemGroup> itemGroupList){
        return itemGroupList.stream()
                .map(this::toItemGroupDTO)
                .toList();
    }
}
