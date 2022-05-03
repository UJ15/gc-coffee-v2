package com.kdt.prgrms.gccoffee.dto;

import com.kdt.prgrms.gccoffee.models.Category;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateProductRequest {

    @NotBlank
    private final String productName;
    @NonNull
    private final Category category;
    @Min(0)
    private final long price;
    private final String description;

    public CreateProductRequest(@NotBlank String productName, @NonNull Category category, long price, String description) {

        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public CreateProductRequest(@NotBlank String productName, @NonNull Category category, long price) {

        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = "";
    }

    @NonNull
    public String getProductName() {

        return productName;
    }

    @NonNull
    public Category getCategory() {

        return category;
    }

    public long getPrice() {

        return price;
    }

    public String getDescription() {

        return description;
    }
}
