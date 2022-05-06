package com.kdt.prgrms.gccoffee.service;

import com.kdt.prgrms.gccoffee.models.Order;
import com.kdt.prgrms.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {

        if (order == null) {
            throw new IllegalArgumentException();
        }

        return orderRepository.save(order);
    }


}
