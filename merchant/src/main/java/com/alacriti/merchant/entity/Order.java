package com.alacriti.merchant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private String orderReference;

    private Long userId;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime createdAt;
}