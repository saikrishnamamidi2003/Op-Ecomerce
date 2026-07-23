package com.alacriti.merchant.service;

import com.alacriti.merchant.dto.UserRequest;
import com.alacriti.merchant.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.alacriti.merchant.entity.User;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String creatUser(UserRequest request){
        int rows = userRepository.save(request);

        if(rows > 0){
            return "User Created Succefully";
        }
        return "User Creation Failed";
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();

    }
}
