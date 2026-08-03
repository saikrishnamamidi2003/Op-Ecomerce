package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.OrderRequest;
import com.alacriti.merchant.dto.OrderResponse;
import com.alacriti.merchant.response.ApiResponse;
import com.alacriti.merchant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkout(
            @RequestBody @Valid OrderRequest request) {

        OrderResponse response = orderService.checkout(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order created successfully",
                        response
                )
        );
    }
}