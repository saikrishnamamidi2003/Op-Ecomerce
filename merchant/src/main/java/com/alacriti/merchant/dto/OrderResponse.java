package com.alacriti.merchant.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderResponse {

    private Long orderId;

    private String orderReference;

    private BigDecimal totalAmount;

    private String status;
}