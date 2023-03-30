package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.service.CredentialsService;
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
    private final CredentialsService credentialsService;

    public ItemsController(ItemsService itemsService, CredentialsService credentialsService) {
        this.itemsService = itemsService;
        this.credentialsService = credentialsService;
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO addAnItem(@RequestBody CreateItemDTO createItemDTO, @RequestHeader String authorization){
        logger.info("Adding an item");
        credentialsService.validateAuthorization(authorization, Feature.ADD_AN_ITEM);
        IdDTO idDTO = itemsService.addItem(createItemDTO);
        logger.info("Item " + idDTO.getId() + " added");
        return idDTO;
    }
}
