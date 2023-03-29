package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class CustomersRepository {

    private Map<String, Customer> customersDB;

    public CustomersRepository() {
        this.customersDB = new HashMap<>();
    }

    public void add(Customer customer) {
        customersDB.put(customer.getId(), customer);
    }

    public Customer getById(String id){
        return customersDB.get(id);
    }
}
