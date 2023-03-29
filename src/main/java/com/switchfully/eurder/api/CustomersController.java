package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.service.CustomersService;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
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

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Customer> getCustomersList(){
        logger.info("Getting the list of all customers");
        return customersService.getCustomersList();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO){
        logger.info("Creating customer");
        return customersService.createCustomer(createCustomerDTO);
    }
}
