package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.PaymentRequest;
import com.alacriti.merchant.dto.PaymentResponse;
import com.alacriti.merchant.response.ApiResponse;
import com.alacriti.merchant.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.createPayment(request, idempotencyKey);

        return new ApiResponse<>(
                true,
                "Payment created successfully",
                response
        );
    }

    @GetMapping("/{paymentReference}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(
            @PathVariable String paymentReference) {

        PaymentResponse response =
                paymentService.getPayment(paymentReference);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment fetched successfully",
                        response
                )
        );
    }
}