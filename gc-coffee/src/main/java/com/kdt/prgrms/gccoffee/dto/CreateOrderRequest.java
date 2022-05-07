package com.kdt.prgrms.gccoffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kdt.prgrms.gccoffee.models.Order;
import com.kdt.prgrms.gccoffee.models.OrderItem;
import com.kdt.prgrms.gccoffee.models.OrderStatus;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateOrderRequest {

    @Email
    private final String email;
    @NotBlank
    @NotNull
    private final String address;
    @NotNull
    @NotBlank
    private final String postcode;
    @NotNull
    private final List<OrderItem> orderItems;

    @JsonCreator
    public CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {

        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
    }

    public Order toDomain() {

        return new Order(this.email,
                this.address,
                this.postcode,
                this.orderItems,
                OrderStatus.ACCEPTED);
    }

    public String getEmail() {

        return email;
    }

    public String getAddress() {

        return address;
    }

    public String getPostcode() {

        return postcode;
    }

    public List<OrderItem> getOrderItems() {

        return orderItems;
    }
}
