package com.kdt.prgrms.gccoffee.service;

import com.kdt.prgrms.gccoffee.exception.custom.NotFoundException;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException();
        }

        return productRepository.save(product);
    }

    public List<Product> getProducts() {

        return productRepository.findAll();
    }

    public Product getProductById(long id) {

        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void updateProductById(long id, Product product) {

        LocalDateTime createdAt = getProductById(id).getCreatedAt();
        Product updateProduct = new Product(id,
                product.getProductName(),
                product.getCategory(),
                product.getPrice(),
                product.getDescription(),
                createdAt,
                product.getUpdatedAt());
        productRepository.save(updateProduct);
    }

    public void deleteProductById(long id) {

        getProductById(id);
        productRepository.deleteById(id);
    }
}
