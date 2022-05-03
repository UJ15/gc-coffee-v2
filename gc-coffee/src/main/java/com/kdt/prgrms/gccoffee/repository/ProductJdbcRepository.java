package com.kdt.prgrms.gccoffee.repository;

import com.kdt.prgrms.gccoffee.models.Product;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Retention;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    @Override
    public Product save(Product product) {
        return null;
    }
}
