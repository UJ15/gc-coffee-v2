package com.kdt.prgrms.gccoffee.controller;

import com.kdt.prgrms.gccoffee.dto.CreateOrderRequest;
import com.kdt.prgrms.gccoffee.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody @Valid CreateOrderRequest orderRequest) {

        orderService.createOrder(orderRequest.toDomain());
    }
}
