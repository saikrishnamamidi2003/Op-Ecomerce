package com.alacriti.merchant.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
