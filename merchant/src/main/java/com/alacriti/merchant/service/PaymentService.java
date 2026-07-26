package com.alacriti.merchant.service;

import com.alacriti.merchant.client.PaymentGatewayClient;
import com.alacriti.merchant.dto.GatewayPaymentRequest;
import com.alacriti.merchant.dto.GatewayPaymentResponse;
import com.alacriti.merchant.dto.PaymentRequest;
import com.alacriti.merchant.dto.PaymentResponse;
import com.alacriti.merchant.entity.Payment;
import com.alacriti.merchant.entity.Product;
import com.alacriti.merchant.enums.PaymentStatus;
import com.alacriti.merchant.exception.ResourceNotFoundException;
import com.alacriti.merchant.repository.PaymentRepository;
import com.alacriti.merchant.repository.ProductRepository;
import com.alacriti.merchant.repository.UserRepository;
import com.alacriti.merchant.util.PaymentReferenceGenerator;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient paymentGatewayClient;

    public PaymentService(UserRepository userRepository,
                          ProductRepository productRepository,
                          PaymentRepository paymentRepository,
                          PaymentGatewayClient paymentGatewayClient) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGatewayClient = paymentGatewayClient;
    }

    public PaymentResponse createPayment(PaymentRequest request) {

        // Validate User
        if (!userRepository.existsById(request.getUserId())) {
            throw new ResourceNotFoundException(
                    "User not found with id " + request.getUserId());
        }

        // Validate Product
        if (!productRepository.existsById(request.getProductId())) {
            throw new ResourceNotFoundException(
                    "Product not found with id " + request.getProductId());
        }

        // Get Product Details
        Product product = productRepository.findById(request.getProductId());

        // Create Payment
        Payment payment = Payment.builder()
                .paymentReference(PaymentReferenceGenerator.generate())
                .userId(request.getUserId())
                .productId(request.getProductId())
                .amount(product.getPrice())
                .currency("USD")
                .status(PaymentStatus.CREATED)
                .build();

        // Save Payment with CREATED status
        paymentRepository.save(payment);

        // Prepare Gateway Request
        GatewayPaymentRequest gatewayRequest = GatewayPaymentRequest.builder()
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .build();

        // Call Payment Gateway
        GatewayPaymentResponse gatewayResponse =
                paymentGatewayClient.processPayment(gatewayRequest);

        // Update Payment Status
        if ("SUCCESS".equalsIgnoreCase(gatewayResponse.getStatus())) {
            payment.setStatus(PaymentStatus.SUCCESS);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        // Update Database
        paymentRepository.updateStatus(
                payment.getPaymentReference(),
                payment.getStatus()
        );

        // Return Response
        return PaymentResponse.builder()
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .build();
    }
}