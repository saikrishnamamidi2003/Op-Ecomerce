package com.alacriti.merchant.client;

import com.alacriti.merchant.dto.GatewayPaymentRequest;
import com.alacriti.merchant.dto.GatewayPaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;


@Slf4j
@Component
public class PaymentGatewayClient {

    private final RestClient restClient;

    public PaymentGatewayClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retryable(
            retryFor = RestClientException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )


    public GatewayPaymentResponse processPayment(
            GatewayPaymentRequest request) {


        log.info("Calling Payment Gateway for payment {}", request.getPaymentReference());
            return restClient.post()
                    .uri("/api/v1/gateway/payments")
                    .body(request)
                    .retrieve()
                    .body(GatewayPaymentResponse.class);


    }

    @Recover
    public GatewayPaymentResponse recover(
            RestClientException exception,
            GatewayPaymentRequest request) {
        log.error("Gateway failed after all retries for payment {}", request.getPaymentReference());

        return GatewayPaymentResponse.builder()
                .paymentReference(request.getPaymentReference())
                .status("FAILED")
                .message("Gateway unavailable after retries")
                .build();
    }



}