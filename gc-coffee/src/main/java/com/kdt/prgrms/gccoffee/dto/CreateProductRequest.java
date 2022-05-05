package com.kdt.prgrms.gccoffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.kdt.prgrms.gccoffee.dto.validation.ValueOfEnum;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateProductRequest {

    @NotBlank
    private final String productName;
    @NonNull
    @ValueOfEnum(enumClass = Category.class)
    private final String category;
    @Min(0)
    private final long price;
    private final String description;

    @JsonCreator
    public CreateProductRequest(@NotBlank String productName, @NonNull String category, long price, String description) {

        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public Product toDomain() {

        return new Product(this.productName, Category.valueOf(this.category), this.price, this.description);
    }
}
