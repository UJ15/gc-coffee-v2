package com.kdt.prgrms.gccoffee.models;

import com.kdt.prgrms.gccoffee.service.OrderService;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final long orderId;
    private final Email email;
    private String address;
    private String postcode;
    private final List<OrderItem> orderItem;

    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(Email email, String address, String postcode, List<OrderItem> orderItem, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.orderId = 0;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItem = orderItem;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getOrderId() {

        return orderId;
    }

    public Email getEmail() {

        return email;
    }

    public String getAddress() {

        return address;
    }

    public String getPostcode() {

        return postcode;
    }

    public List<OrderItem> getOrderItem() {

        return orderItem;
    }

    public OrderStatus getOrderStatus() {

        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;
    }

    public void updateAddress(String address) {

        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePostcode(String postcode) {

        this.postcode = postcode;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateOrderStatus(OrderStatus orderStatus) {

        this.orderStatus = orderStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
