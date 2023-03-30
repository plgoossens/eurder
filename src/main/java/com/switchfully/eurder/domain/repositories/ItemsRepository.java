package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.models.Order;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.switchfully.eurder.service.Utils.isUUIDValid;

@Repository
public class ItemsRepository {

    private final Map<UUID, Item> itemsDB;

    public ItemsRepository() {
        itemsDB = new HashMap<>();
    }

    public void add(Item item){
        itemsDB.put(item.getId(), item);
    }

    public Collection<Item> getAllItems(){
        return itemsDB.values();
    }

    public Optional<Item> getById(String id){
        if(!isUUIDValid(id)){
            throw new ItemNotFoundException("Item with id " + id + " was not found.");
        }
        return Optional.ofNullable(itemsDB.get(UUID.fromString(id)));
    }

    public void updateStockForOrder(Order order){
        order.getItems().forEach(
                itemGroup ->
                        itemGroup.getItem().setAmount(
                                itemGroup.getItem().getAmount()-itemGroup.getAmount()));
    }
}
