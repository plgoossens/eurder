package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.service.CredentialsService;
import com.switchfully.eurder.service.CustomersService;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.wrappers.CreateCustomerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private final Logger logger = LoggerFactory.getLogger(CustomersController.class);

    private final CustomersService customersService;
    private final CredentialsService credentialsService;

    public CustomersController(CustomersService customersService, CredentialsService credentialsService) {
        this.customersService = customersService;
        this.credentialsService = credentialsService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Customer> getCustomersList(@RequestHeader String authorization){
        logger.info("Getting the list of all customers");
        credentialsService.validateAuthorization(authorization, Feature.VIEW_ALL_CUSTOMERS);
        return customersService.getCustomersList();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO createCustomer(@RequestBody CreateCustomerWrapper createCustomerWrapper){
        logger.info("Creating customer");
        return customersService.createCustomer(createCustomerWrapper);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable String id, @RequestHeader String authorization){
        logger.info("Getting the customer with id " + id);
        credentialsService.validateAuthorization(authorization, Feature.VIEW_A_SINGLE_CUSTOMER);
        return customersService.getCustomerById(id);
    }
}
