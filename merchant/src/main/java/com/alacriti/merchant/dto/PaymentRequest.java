package com.alacriti.merchant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

}