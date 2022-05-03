package com.kdt.prgrms.gccoffee.controller;


import com.kdt.prgrms.gccoffee.dto.CreateProductRequest;
import com.kdt.prgrms.gccoffee.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {

        this.productService = productService;
    }

    @PostMapping
    public void createProduct(@RequestBody @Valid CreateProductRequest request) {

        productService.createProduct(request.toDomain());
    }
}
