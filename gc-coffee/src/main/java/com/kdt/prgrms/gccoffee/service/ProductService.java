package com.kdt.prgrms.gccoffee.service;

import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        throw new IllegalArgumentException();
    }

    public void deleteById(long id) {

    }
}
