package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ItemsRepository {

    private Map<UUID, Item> itemsDB;

    public ItemsRepository() {
        itemsDB = new HashMap<>();
    }

    public void add(Item item){
        itemsDB.put(item.getId(), item);
    }
}
