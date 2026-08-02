package com.alacriti.merchant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull
    private Long userId;
}