package com.alacriti.merchant.entity;

import com.alacriti.merchant.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment {

    private Long id;

    private String paymentReference;

    private Long userId;

    private Long productId;

    private BigDecimal amount;

    private String currency;

    private PaymentStatus status;

    private String idempotencyKey;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}