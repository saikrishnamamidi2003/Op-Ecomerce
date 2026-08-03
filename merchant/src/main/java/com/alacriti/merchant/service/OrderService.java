package com.alacriti.merchant.service;

import com.alacriti.merchant.dto.OrderRequest;
import com.alacriti.merchant.dto.OrderResponse;
import com.alacriti.merchant.entity.Cart;
import com.alacriti.merchant.entity.Order;
import com.alacriti.merchant.entity.OrderItem;
import com.alacriti.merchant.entity.Product;
import com.alacriti.merchant.exception.ResourceNotFoundException;
import com.alacriti.merchant.repository.CartRepository;
import com.alacriti.merchant.repository.OrderItemRepository;
import com.alacriti.merchant.repository.OrderRepository;
import com.alacriti.merchant.repository.ProductRepository;
import com.alacriti.merchant.repository.UserRepository;
import com.alacriti.merchant.util.OrderReferenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(UserRepository userRepository,
                        ProductRepository productRepository,
                        CartRepository cartRepository,
                        OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderResponse checkout(OrderRequest request) {

        if (!userRepository.existsById(request.getUserId())) {
            throw new ResourceNotFoundException(
                    "User not found with id " + request.getUserId());
        }

        List<Cart> cartItems =
                cartRepository.getCartByUser(request.getUserId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Cart cart : cartItems) {

            Product product =
                    productRepository.findById(cart.getProductId());

            BigDecimal itemTotal =
                    product.getPrice().multiply(
                            BigDecimal.valueOf(cart.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);
        }

        Order order = Order.builder()
                .orderReference(OrderReferenceGenerator.generate())
                .userId(request.getUserId())
                .totalAmount(totalAmount)
                .status("CREATED")
                .build();

        Long orderId = orderRepository.save(order);

        for (Cart cart : cartItems) {

            Product product =
                    productRepository.findById(cart.getProductId());

            OrderItem orderItem = OrderItem.builder()
                    .orderId(orderId)
                    .productId(product.getId())
                    .quantity(cart.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }

        cartRepository.clearCart(request.getUserId());

        return OrderResponse.builder()
                .orderId(orderId)
                .orderReference(order.getOrderReference())
                .totalAmount(totalAmount)
                .status("CREATED")
                .build();
    }

    public OrderResponse getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new ResourceNotFoundException(
                    "Order not found with id " + orderId);
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderReference(order.getOrderReference())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }
}