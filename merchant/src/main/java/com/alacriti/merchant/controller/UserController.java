package com.alacriti.merchant.controller;

import com.alacriti.merchant.dto.UserRequest;
import com.alacriti.merchant.dto.UserResponse;
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
    public ApiResponse<List<UserResponse>> getUsers() {

        List<UserResponse> users = userService.getAllUsers();

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                users
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {

        UserResponse user = userService.getUserById(id);

        return new ApiResponse<>(
                true,
                "User fetched successfully",
                user
        );
    }

//    @GetMapping("/{id}")
//    public ApiResponse<UserResponse> getUserById(
//            @PathVariable Long id) {
//
//        UserResponse response = userService.getUserById(id);
//
//        return new ApiResponse<>(
//                true,
//                "User fetched successfully",
//                response
//        );
//
//    }
}
