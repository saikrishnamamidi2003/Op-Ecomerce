package com.alacriti.merchant.repository;

import com.alacriti.merchant.entity.Payment;
import com.alacriti.merchant.enums.PaymentStatus;
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
                    status
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?
                )
                """;

        return jdbcTemplate.update(
                sql,
                payment.getPaymentReference(),
                payment.getUserId(),
                payment.getProductId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus().name()
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

}
