package com.kdt.prgrms.gccoffee.controller;


import com.kdt.prgrms.gccoffee.dto.CreateProductRequest;
import com.kdt.prgrms.gccoffee.dto.ProductResponse;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.service.ProductService;
import org.springframework.lang.Nullable;
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
    public List<ProductResponse> getAllProducts(@RequestParam @Nullable Category category, @RequestParam @Nullable String name) {

        if (category != null && name != null) {
            return productService.getProducts()
                    .stream()
                    .filter(product -> product.getProductName().equals(name))
                    .filter(product -> product.getCategory().equals(category))
                    .map(ProductResponse::from)
                    .toList();
        }
        if (category != null) {
            return productService.getProducts()
                    .stream()
                    .filter(product -> product.getCategory().equals(category))
                    .map(ProductResponse::from)
                    .toList();
        }
        if (name != null) {
            return productService.getProducts()
                    .stream()
                    .filter(product -> product.getProductName().equals(name))
                    .map(ProductResponse::from)
                    .toList();
        }

        return productService.getProducts().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable long id) {

        Product product = productService.getProductById(id);

        return ProductResponse.from(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable long id) {

        productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    public void updateProductById(@PathVariable long id) {


    }


}
