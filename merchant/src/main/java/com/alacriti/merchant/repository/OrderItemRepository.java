package com.alacriti.merchant.repository;

import com.alacriti.merchant.entity.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(OrderItem orderItem) {

        String sql = """
                INSERT INTO order_items
                (
                    order_id,
                    product_id,
                    quantity,
                    price
                )
                VALUES
                (
                    ?, ?, ?, ?
                )
                """;

        return jdbcTemplate.update(
                sql,
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}