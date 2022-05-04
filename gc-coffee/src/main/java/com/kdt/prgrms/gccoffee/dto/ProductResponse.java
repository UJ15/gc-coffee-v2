package com.kdt.prgrms.gccoffee.dto;

import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;

import java.time.LocalDateTime;

public class ProductResponse {

    private final long productId;
    private final String productName;
    private final Category category;
    private final long price;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductResponse(long productId, String productName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductResponse from(Product product) {

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getCategory(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
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
}
