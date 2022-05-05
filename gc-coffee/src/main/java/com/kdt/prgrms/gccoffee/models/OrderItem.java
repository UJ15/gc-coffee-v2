package com.kdt.prgrms.gccoffee.models;

import java.time.LocalDateTime;

public class OrderItem {

    private final long productId;
    private final Category category;
    private final long price;
    private final int quantity;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderItem(long productId, Category category, long price, int quantity) {

        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
