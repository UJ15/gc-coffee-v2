package com.kdt.prgrms.gccoffee.models;


import java.time.LocalDateTime;

public class Product {

    private final long productId;
    private String productName;
    private Category category;
    private final long price;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(long productId, String productName, Category category, long price) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(long productId, String productName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getProductId() {

        return productId;
    }

    public String getProductName() {

        return productName;
    }

    public Category getCategory() {

        return category;
    }

    public long getPrice() {

        return price;
    }

    public String getDescription() {

        return description;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;
    }

    public void updateProductName(String productName) {

        this.productName = productName;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(String description) {

        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCategory(Category category) {

        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }
}
