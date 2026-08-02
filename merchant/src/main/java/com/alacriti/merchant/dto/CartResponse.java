package com.alacriti.merchant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {

    private Long cartId;

    private Long userId;

    private Long productId;

    private Integer quantity;
}