package com.alacriti.merchant.service;

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

    public PaymentService(UserRepository userRepository,
                          ProductRepository productRepository,
                          PaymentRepository paymentRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse createPayment(PaymentRequest request) {

        if (!userRepository.existsById(request.getUserId())) {
            throw new ResourceNotFoundException(
                    "User not found with id " + request.getUserId());
        }

        if (!productRepository.existsById(request.getProductId())) {
            throw new ResourceNotFoundException(
                    "Product not found with id " + request.getProductId());
        }

        Product product = productRepository.findById(request.getProductId());

        Payment payment = Payment.builder()
                .paymentReference(PaymentReferenceGenerator.generate())
                .userId(request.getUserId())
                .productId(request.getProductId())
                .amount(product.getPrice())
                .currency("USD")
                .status(PaymentStatus.CREATED)
                .build();

        paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .build();
    }

}