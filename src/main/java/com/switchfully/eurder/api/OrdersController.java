package com.switchfully.eurder.api;

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

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody CreateOrderDTO createOrderDTO){
        logger.info("Creating an order");
        return ordersService.createOrder(createOrderDTO);
    }

}
