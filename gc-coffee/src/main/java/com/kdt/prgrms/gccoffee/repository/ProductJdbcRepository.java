package com.kdt.prgrms.gccoffee.repository;

import com.kdt.prgrms.gccoffee.models.Product;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private static final String INSERT_SQL = "INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at)"
            + "VALUES(:productId, :productName, :category, :price, :description, :createdAt, :updatedAt)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product product) {

        if (product == null) {
            throw new IllegalArgumentException();
        }
        if (product.getProductId() == 0) {
            return insertProduct(product);
        }
        return updateProduct(product);
    }

    private Product insertProduct(Product product) {

        int update = jdbcTemplate.update(INSERT_SQL, toParamMap(product));

        if (update != 1) {
            throw new IllegalStateException();
        }

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Collections.emptyMap(), Long.class);

        return Product.toEntity(id, product);
    }

    private Product updateProduct(Product product) {

        return null;
    }

    private Map<String, Object> toParamMap(Product product) {

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("productId", product.getProductId());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());

        return paramMap;
    }
}
