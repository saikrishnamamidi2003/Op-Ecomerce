package com.alacriti.merchant.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Product {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
