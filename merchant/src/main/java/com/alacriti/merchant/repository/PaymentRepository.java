package com.alacriti.merchant.repository;

import com.alacriti.merchant.entity.Payment;
import com.alacriti.merchant.enums.PaymentStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Payment payment) {

        String sql = """
                INSERT INTO payments
                (
                    payment_reference,
                    user_id,
                    product_id,
                    amount,
                    currency,
                    status,
                    idempotency_key
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?
                )
                """;

        return jdbcTemplate.update(
                sql,
                payment.getPaymentReference(),
                payment.getUserId(),
                payment.getProductId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus().name(),
                payment.getIdempotencyKey()
        );
    }

    public int updateStatus(String paymentReference,
                            PaymentStatus status) {

        String sql = """
            UPDATE payments
            SET status = ?
            WHERE payment_reference = ?
            """;

        return jdbcTemplate.update(
                sql,
                status.name(),
                paymentReference
        );
    }

    public Payment findByIdempotencyKey(String idempotencyKey) {

        String sql = """
            SELECT *
            FROM payments
            WHERE idempotency_key = ?
            """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Payment.class),
                    idempotencyKey
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
