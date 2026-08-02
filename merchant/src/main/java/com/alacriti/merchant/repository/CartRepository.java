package com.alacriti.merchant.repository;

import com.alacriti.merchant.entity.Cart;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addToCart(Cart cart) {

        String sql = """
                INSERT INTO cart
                (
                    user_id,
                    product_id,
                    quantity
                )
                VALUES
                (
                    ?, ?, ?
                )
                """;

        return jdbcTemplate.update(
                sql,
                cart.getUserId(),
                cart.getProductId(),
                cart.getQuantity()
        );
    }

    public Cart findByUserAndProduct(Long userId, Long productId) {

        String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                AND product_id = ?
                """;

        try {

            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Cart.class),
                    userId,
                    productId
            );

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public int updateQuantity(Long cartId, Integer quantity) {

        String sql = """
                UPDATE cart
                SET quantity = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                quantity,
                cartId
        );
    }

    public List<Cart> getCartByUser(Long userId) {

        String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Cart.class),
                userId
        );
    }

    public int removeFromCart(Long cartId) {

        String sql = """
                DELETE FROM cart
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                cartId
        );
    }

    public int clearCart(Long userId) {

        String sql = """
                DELETE FROM cart
                WHERE user_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                userId
        );
    }
}