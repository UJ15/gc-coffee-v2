package com.kdt.prgrms.gccoffee.controller;


import com.kdt.prgrms.gccoffee.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {

        this.productService = productService;
    }
}
