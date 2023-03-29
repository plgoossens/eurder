package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.service.CredentialsService;
import com.switchfully.eurder.service.OrdersService;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    private final OrdersService ordersService;
    private final CredentialsService credentialsService;

    public OrdersController(OrdersService ordersService, CredentialsService credentialsService) {
        this.ordersService = ordersService;
        this.credentialsService = credentialsService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody CreateOrderDTO createOrderDTO, @RequestHeader String authorization){
        logger.info("Creating an order");
        String customerId = credentialsService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        return ordersService.createOrder(createOrderDTO, customerId);
    }

}
