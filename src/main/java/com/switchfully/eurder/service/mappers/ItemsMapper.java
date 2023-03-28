package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemsMapper {
    public Item toDomain(CreateItemDTO createItemDTO) {
        return new Item(createItemDTO.getName(), createItemDTO.getDescription(), createItemDTO.getPrice(), createItemDTO.getAmount());
    }

    public IdDTO toIdDTO(Item item) {
        return new IdDTO(item.getId().toString());
    }
}
