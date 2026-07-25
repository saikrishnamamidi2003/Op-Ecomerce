package com.alacriti.orbipay_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayPaymentRequest {

    private String paymentReference;
    private BigDecimal amount;
    private String currency;

}