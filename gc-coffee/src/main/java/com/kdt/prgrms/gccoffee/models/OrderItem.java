package com.kdt.prgrms.gccoffee.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class OrderItem {

    private final long productId;
    private final Category category;
    private final long price;
    private final int quantity;

    @JsonCreator
    public OrderItem(long productId, Category category, long price, int quantity) {

        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
