package com.alacriti.merchant.repository;

import com.alacriti.merchant.entity.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Order order) {

        String sql = """
                INSERT INTO orders
                (
                    order_reference,
                    user_id,
                    total_amount,
                    status
                )
                VALUES
                (
                    ?, ?, ?, ?
                )
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );

            ps.setString(1, order.getOrderReference());
            ps.setLong(2, order.getUserId());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());

            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Order findById(Long orderId) {

        String sql = """
                SELECT *
                FROM orders
                WHERE id = ?
                """;


        try {

            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Order.class),
                    orderId
            );

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}