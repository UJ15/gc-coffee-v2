package com.kdt.prgrms.gccoffee.controller;


import com.kdt.prgrms.gccoffee.dto.CreateProductRequest;
import com.kdt.prgrms.gccoffee.dto.ProductResponse;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    public List<ProductResponse> getAllProducts() {

        return productService.getProducts().stream()
                .map(ProductResponse::from)
                .toList();
    }


}
