package com.alacriti.merchant.repository;

import com.alacriti.merchant.dto.ProductRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(ProductRequest request) {

        String sql = """
                INSERT INTO products
                (
                    name,
                    description,
                    price,
                    stock
                )
                VALUES
                (
                    ?, ?, ?, ?
                )
                """;

        return jdbcTemplate.update(
                sql,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );
    }

    public boolean existsByName(String name) {

        String sql = """
                SELECT COUNT(*)
                FROM products
                WHERE name = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                name
        );

        return count != null && count > 0;
    }
}