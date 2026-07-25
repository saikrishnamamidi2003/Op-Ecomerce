package com.alacriti.orbipay_gateway.controller;

import com.alacriti.orbipay_gateway.dto.GatewayPaymentRequest;
import com.alacriti.orbipay_gateway.dto.GatewayPaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gateway/payments")
public class PaymentGatewayController {

    @PostMapping
    public GatewayPaymentResponse processPayment(
            @RequestBody GatewayPaymentRequest request) {

        return GatewayPaymentResponse.builder()
                .paymentReference(request.getPaymentReference())
                .status("SUCCESS")
                .message("Payment processed successfully")
                .build();
    }
}