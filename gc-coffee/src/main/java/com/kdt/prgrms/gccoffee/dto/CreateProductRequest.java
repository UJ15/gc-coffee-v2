package com.kdt.prgrms.gccoffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateProductRequest {

    @NotBlank
    @NotNull
    private final String productName;
    @NotNull
    private final Category category;
    @Min(0)
    private final long price;
    private final String description;

    @JsonCreator
    public CreateProductRequest(@NotBlank String productName, @NotNull Category category, long price, String description) {

        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public Product toDomain() {

        return new Product(this.productName, this.category, this.price, this.description);
    }
}
