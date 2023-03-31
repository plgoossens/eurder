package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.domain.models.UrgencyLevel;
import com.switchfully.eurder.service.CredentialsService;
import com.switchfully.eurder.service.ItemsService;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

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

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDTO> getItemOverview(@RequestParam Optional<UrgencyLevel> urgencyLevel, @RequestHeader String authorization){
        logger.info("Getting item overview");
        credentialsService.validateAuthorization(authorization, Feature.GET_ITEM_OVERVIEW);
        return itemsService.getItemOverview(urgencyLevel);
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

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateAnItem(@RequestBody CreateItemDTO createItemDTO, @PathVariable String id, @RequestHeader String authorization){
        logger.info("Updating item " + id);
        credentialsService.validateAuthorization(authorization, Feature.UPDATE_AN_ITEM);
        itemsService.updateItem(createItemDTO, id);
        logger.info("Item " + id + " updated");
    }
}
