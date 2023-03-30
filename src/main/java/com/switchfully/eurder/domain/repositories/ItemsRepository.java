package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Item;
import com.switchfully.eurder.domain.models.Order;
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

    public void updateStockForOrder(Order order){
        order.getItems().forEach(
                itemGroup ->
                        itemGroup.getItem().setAmount(
                                itemGroup.getItem().getAmount()-itemGroup.getAmount()));
    }
}
