package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.UserRequest;
import com.alacriti.merchant.entity.User;
import com.alacriti.merchant.response.ApiResponse;
import com.alacriti.merchant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@Valid @RequestBody UserRequest request){
        return userService.creatUser((request));
    }



    @GetMapping
    public ApiResponse<List<User>> getUsers(){

        List<User> users = userService.getAllUsers();

        return ApiResponse.<List<User>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(users)
                .build();
    }
}
