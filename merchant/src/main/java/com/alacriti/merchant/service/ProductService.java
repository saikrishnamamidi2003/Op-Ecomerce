package com.alacriti.merchant.service;

import com.alacriti.merchant.dto.ProductRequest;
import com.alacriti.merchant.exception.DuplicateProductException;
import com.alacriti.merchant.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String createProduct(ProductRequest request) {

        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateProductException(
                    "Product already exists : " + request.getName()
            );
        }

        int rows = productRepository.save(request);

        if (rows > 0) {
            return "Product Created Successfully";
        }

        return "Product Creation Failed";
    }

}
