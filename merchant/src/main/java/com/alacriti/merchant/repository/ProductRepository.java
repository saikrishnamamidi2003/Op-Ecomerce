package com.alacriti.merchant.repository;

import com.alacriti.merchant.dto.ProductRequest;
import com.alacriti.merchant.entity.Product;
import com.alacriti.merchant.mapper.ProductRowMapper;
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

    public boolean existsById(Long id) {

        String sql = """
            SELECT COUNT(*)
            FROM products
            WHERE id = ?
            """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return count != null && count > 0;
    }

    public Product findById(Long id) {

        String sql = """
            SELECT *
            FROM products
            WHERE id = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                new ProductRowMapper(),
                id
        );
    }
}