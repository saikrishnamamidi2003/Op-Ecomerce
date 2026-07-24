package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.PaymentRequest;
import com.alacriti.merchant.dto.PaymentResponse;
import com.alacriti.merchant.response.ApiResponse;
import com.alacriti.merchant.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ApiResponse<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.createPayment(request);

        return new ApiResponse<>(
                true,
                "Payment created successfully",
                response
        );
    }
}