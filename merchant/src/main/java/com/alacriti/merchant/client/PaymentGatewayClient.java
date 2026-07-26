package com.alacriti.merchant.client;

import com.alacriti.merchant.dto.GatewayPaymentRequest;
import com.alacriti.merchant.dto.GatewayPaymentResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PaymentGatewayClient {

    private final RestClient restClient;

    public PaymentGatewayClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public GatewayPaymentResponse processPayment(
            GatewayPaymentRequest request) {

        return restClient.post()
                .uri("/api/v1/gateway/payments")
                .body(request)
                .retrieve()
                .body(GatewayPaymentResponse.class);

    }

}