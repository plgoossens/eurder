package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemsRepository {

    private final Map<String, Item> itemsDB;

    public ItemsRepository() {
        itemsDB = new HashMap<>();
    }

    public void add(Item item){
        itemsDB.put(item.getId(), item);
    }

    public Optional<Item> getById(String id){
        return Optional.ofNullable(itemsDB.get(id));
    }
}
