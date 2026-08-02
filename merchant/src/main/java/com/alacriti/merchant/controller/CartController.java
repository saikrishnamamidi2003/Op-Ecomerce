package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.CartRequest;
import com.alacriti.merchant.dto.CartResponse;
import com.alacriti.merchant.response.ApiResponse;
import com.alacriti.merchant.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @RequestBody @Valid CartRequest request) {

        CartResponse response = cartService.addToCart(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product added to cart successfully",
                        response
                )
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<CartResponse>>> getCart(
            @PathVariable Long userId) {

        List<CartResponse> response = cartService.getCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart fetched successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<String>> removeFromCart(
            @PathVariable Long cartId) {

        cartService.removeFromCart(cartId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart item removed successfully",
                        "SUCCESS"
                )
        );
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<String>> clearCart(
            @PathVariable Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart cleared successfully",
                        "SUCCESS"
                )
        );
    }
}