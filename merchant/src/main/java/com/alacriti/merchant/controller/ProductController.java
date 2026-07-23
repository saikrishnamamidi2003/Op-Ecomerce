package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.ProductRequest;
import com.alacriti.merchant.response.ApiResponse;
import com.alacriti.merchant.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ApiResponse<String> createProduct(
            @Valid @RequestBody ProductRequest request) {

        String response = productService.createProduct(request);

        return new ApiResponse<>(
                true,
                response,
                null
        );
    }

}