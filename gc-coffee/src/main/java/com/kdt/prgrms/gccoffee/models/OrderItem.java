package com.kdt.prgrms.gccoffee.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class OrderItem {

    private final long productId;
    private final int quantity;

    @JsonCreator
    public OrderItem(long productId, int quantity) {

        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
