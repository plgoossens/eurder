package com.switchfully.eurder.domain.repositories;

import com.switchfully.eurder.domain.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomersRepository {

    private final Map<String, Customer> customersDB;

    public CustomersRepository() {
        this.customersDB = new HashMap<>();
    }

    public Collection<Customer> getCustomersList(){
        return customersDB.values();
    }

    public void add(Customer customer) {
        customersDB.put(customer.getId(), customer);
    }

    public Optional<Customer> getById(String id){
        return Optional.ofNullable(customersDB.get(id));
    }
}
