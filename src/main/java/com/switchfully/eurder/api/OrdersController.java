package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Feature;
import com.switchfully.eurder.service.CredentialsService;
import com.switchfully.eurder.service.OrdersService;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.ItemGroupOrderDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import com.switchfully.eurder.service.dto.OrderReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderReportDTO getOrderReport(@RequestHeader String authorization){
        logger.info("Customer getting orders report");
        UUID customerId = credentialsService.validateAuthorization(authorization, Feature.VIEW_ORDERS_REPORT);
        logger.info("Customer " + customerId + " authorized to get orders report");
        return ordersService.getOrdersReport(customerId);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody CreateOrderDTO createOrderDTO, @RequestHeader String authorization){
        logger.info("Creating an order");
        UUID customerId = credentialsService.validateAuthorization(authorization, Feature.ORDER_ITEMS);
        logger.info("Customer " + customerId + " authorized to create an order");
        return ordersService.createOrder(createOrderDTO, customerId);
    }

    @GetMapping(path="/items", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemGroupOrderDTO> getOrderItems(@RequestParam Optional<LocalDate> shippingDate, @RequestHeader String authorization){
        logger.info("Getting order items");
        credentialsService.validateAuthorization(authorization, Feature.GET_ORDER_ITEMS);
        return ordersService.getOrderItems(shippingDate);
    }

}
