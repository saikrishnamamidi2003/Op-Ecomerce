package com.alacriti.merchant.service;

import com.alacriti.merchant.client.PaymentGatewayClient;
import com.alacriti.merchant.dto.GatewayPaymentRequest;
import com.alacriti.merchant.dto.GatewayPaymentResponse;
import com.alacriti.merchant.dto.PaymentRequest;
import com.alacriti.merchant.dto.PaymentResponse;
import com.alacriti.merchant.entity.Order;
import com.alacriti.merchant.entity.Payment;
import com.alacriti.merchant.entity.Product;
import com.alacriti.merchant.enums.PaymentStatus;
import com.alacriti.merchant.exception.ResourceNotFoundException;
import com.alacriti.merchant.repository.OrderRepository;
import com.alacriti.merchant.repository.PaymentRepository;
import com.alacriti.merchant.repository.ProductRepository;
import com.alacriti.merchant.repository.UserRepository;
import com.alacriti.merchant.util.PaymentReferenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient paymentGatewayClient;

    public PaymentService(UserRepository userRepository,
                          OrderRepository orderRepository,
                          PaymentRepository paymentRepository,
                          PaymentGatewayClient paymentGatewayClient) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGatewayClient = paymentGatewayClient;
    }



    @Transactional
    public PaymentResponse createPayment(PaymentRequest request, String idempotencyKey) {

        Payment existingPayment =
                paymentRepository.findByIdempotencyKey(idempotencyKey);

        if (existingPayment != null) {

            return PaymentResponse.builder()
                    .paymentReference(existingPayment.getPaymentReference())
                    .amount(existingPayment.getAmount())
                    .currency(existingPayment.getCurrency())
                    .status(existingPayment.getStatus())
                    .build();
        }

        // Validate User
        if (!userRepository.existsById(request.getUserId())) {
            throw new ResourceNotFoundException(
                    "User not found with id " + request.getUserId());
        }

        // Validate Product
        Order order = orderRepository.findById(request.getOrderId());

        if (order == null) {
            throw new ResourceNotFoundException(
                    "Order not found with id " + request.getOrderId());
        }

        // Get Product Details
        //Product product = productRepository.findById(request.getOrderId());

        // Create Payment
        Payment payment = Payment.builder()
                .paymentReference(PaymentReferenceGenerator.generate())
                .userId(request.getUserId())
                .orderId(request.getOrderId())
                .amount(order.getTotalAmount())
                .currency("USD")
                .status(PaymentStatus.CREATED)
                .idempotencyKey(idempotencyKey)
                .build();

        // Save Payment with CREATED status
        paymentRepository.save(payment);


// Temporary - for testing transaction rollback
//        if (true) {
//            throw new RuntimeException("Testing transaction rollback");
//        }



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

    public PaymentResponse getPayment(String paymentReference) {

        Payment payment =
                paymentRepository.findByReference(paymentReference);

        if (payment == null) {
            throw new ResourceNotFoundException(
                    "Payment not found");
        }

        return PaymentResponse.builder()
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .build();
    }
}