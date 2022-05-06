package com.kdt.prgrms.gccoffee.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final long orderId;
    private final UserEmail email;
    private String address;
    private String postcode;
    private final List<OrderItem> orderItem;

    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonCreator
    public Order(UserEmail email, String address, String postcode, List<OrderItem> orderItem, OrderStatus orderStatus) {

        this.orderId = 0;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItem = orderItem;
        this.orderStatus = orderStatus;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Order(long orderId, UserEmail email, String address, String postcode, List<OrderItem> orderItem, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItem = orderItem;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Order toEntity(Long id, Order order) {

        return new Order(id,
                order.getEmail(),
                order.getAddress(),
                order.getPostcode(),
                order.getOrderItem(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt());
    }

    public long getOrderId() {

        return orderId;
    }

    public UserEmail getEmail() {

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
