package com.alacriti.merchant.service;

import com.alacriti.merchant.dto.CartRequest;
import com.alacriti.merchant.dto.CartResponse;
import com.alacriti.merchant.entity.Cart;
import com.alacriti.merchant.exception.ResourceNotFoundException;
import com.alacriti.merchant.repository.CartRepository;
import com.alacriti.merchant.repository.ProductRepository;
import com.alacriti.merchant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartResponse addToCart(CartRequest request) {

        if (!userRepository.existsById(request.getUserId())) {
            throw new ResourceNotFoundException(
                    "User not found with id " + request.getUserId());
        }

        if (!productRepository.existsById(request.getProductId())) {
            throw new ResourceNotFoundException(
                    "Product not found with id " + request.getProductId());
        }

        Cart existingCart = cartRepository.findByUserAndProduct(
                request.getUserId(),
                request.getProductId());

        if (existingCart != null) {

            Integer updatedQuantity =
                    existingCart.getQuantity() + request.getQuantity();

            cartRepository.updateQuantity(
                    existingCart.getId(),
                    updatedQuantity);

            return CartResponse.builder()
                    .cartId(existingCart.getId())
                    .userId(existingCart.getUserId())
                    .productId(existingCart.getProductId())
                    .quantity(updatedQuantity)
                    .build();
        }

        Cart cart = Cart.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();

        cartRepository.addToCart(cart);

        Cart savedCart = cartRepository.findByUserAndProduct(
                request.getUserId(),
                request.getProductId());

        return CartResponse.builder()
                .cartId(savedCart.getId())
                .userId(savedCart.getUserId())
                .productId(savedCart.getProductId())
                .quantity(savedCart.getQuantity())
                .build();
    }

    public List<CartResponse> getCart(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id " + userId);
        }

        return cartRepository.getCartByUser(userId)
                .stream()
                .map(cart -> CartResponse.builder()
                        .cartId(cart.getId())
                        .userId(cart.getUserId())
                        .productId(cart.getProductId())
                        .quantity(cart.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public void removeFromCart(Long cartId) {

        cartRepository.removeFromCart(cartId);
    }

    public void clearCart(Long userId) {

        cartRepository.clearCart(userId);
    }
}