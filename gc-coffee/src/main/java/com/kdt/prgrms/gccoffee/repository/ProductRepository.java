package com.kdt.prgrms.gccoffee.repository;

import com.kdt.prgrms.gccoffee.models.Product;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAll();
}
