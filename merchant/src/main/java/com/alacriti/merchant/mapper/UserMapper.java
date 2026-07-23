package com.alacriti.merchant.mapper;

import com.alacriti.merchant.dto.UserResponse;
import com.alacriti.merchant.entity.User;

public class UserMapper {
    private UserMapper(){

    }

    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
