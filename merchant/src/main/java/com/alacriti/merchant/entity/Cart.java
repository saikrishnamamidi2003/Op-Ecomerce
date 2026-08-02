package com.alacriti.merchant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private LocalDateTime createdAt;
}