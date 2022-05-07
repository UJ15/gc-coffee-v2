package com.kdt.prgrms.gccoffee.repository;

import com.kdt.prgrms.gccoffee.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(long id);

    void deleteById(long id);
}
