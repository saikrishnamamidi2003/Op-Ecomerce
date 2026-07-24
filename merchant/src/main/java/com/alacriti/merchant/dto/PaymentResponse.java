package com.alacriti.merchant.dto;

import com.alacriti.merchant.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse {

    private String paymentReference;

    private BigDecimal amount;

    private String currency;

    private PaymentStatus status;

}