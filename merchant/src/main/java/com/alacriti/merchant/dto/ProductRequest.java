package com.alacriti.merchant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
}