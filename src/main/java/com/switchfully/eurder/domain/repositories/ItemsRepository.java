package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        return Optional.ofNullable(itemsDB.get(UUID.fromString(id)));
    }
}
