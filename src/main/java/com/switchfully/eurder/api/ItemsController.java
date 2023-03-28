package com.switchfully.eurder.api;

import com.switchfully.eurder.service.ItemsService;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemsController {

    private final Logger logger = LoggerFactory.getLogger(ItemsController.class);
    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO addAnItem(@RequestBody CreateItemDTO createItemDTO){
        logger.info("Adding an item");
        return itemsService.addItem(createItemDTO);
    }
}
