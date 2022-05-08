package com.kdt.prgrms.gccoffee.repository;

import com.kdt.prgrms.gccoffee.models.Order;
import com.kdt.prgrms.gccoffee.models.OrderItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private static final String ORDER_INSERT_SQL = "INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at)" +
            "VALUES(:orderId, :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)";
    private static final String ITEM_INSERT_SQL = "INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) " +
            " VALUES (:orderId, :productId, :category, :price, :quantity, :createdAt, :updatedAt)";
    private static final String LAST_ID_SQL = "SELECT LAST_INSERT_ID()";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order save(Order order) {

        if (order == null) {
            throw new IllegalArgumentException();
        }
        if (order.getOrderId() == 0) {
            return insertOrder(order);
        }
        return updateOrder(order);
    }

    private Order updateOrder(Order order) {

        return null;
    }

    private Order insertOrder(Order order) {

        int update = jdbcTemplate.update(ORDER_INSERT_SQL, toOrderParamMap(order));

        if (update != 1) {
            throw new IllegalStateException();
        }

        long id = Optional.ofNullable(jdbcTemplate.queryForObject(LAST_ID_SQL, Collections.emptyMap(), long.class))
                .orElseThrow(IllegalStateException::new);

        order.getOrderItem()
                .forEach(
                        item -> jdbcTemplate.update(ITEM_INSERT_SQL,
                                toOrderItemParamMap(id, order.getCreatedAt(), order.getUpdatedAt(), item))
                );

        return Order.toEntity(id, order);
    }

    private Map<String, Object> toOrderParamMap(Order order) {

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("orderId", order.getOrderId());
        paramMap.put("email", order.getEmail().toString());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());

        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(long orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem item) {

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("orderId", orderId);
        paramMap.put("productId", item.getProductId());
        paramMap.put("category", item.getCategory().toString());
        paramMap.put("price", item.getPrice());
        paramMap.put("quantity", item.getQuantity());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);

        return paramMap;
    }
}
