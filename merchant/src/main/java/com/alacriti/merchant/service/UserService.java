package com.alacriti.merchant.service;

import com.alacriti.merchant.dto.UserRequest;
import com.alacriti.merchant.dto.UserResponse;
import com.alacriti.merchant.exception.DuplicateEmailException;
import com.alacriti.merchant.exception.ResourceNotFoundException;
import com.alacriti.merchant.mapper.UserMapper;
import com.alacriti.merchant.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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

        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException(
                    "Email alredy exists: " + request.getEmail()
            );
        }

        int rows = userRepository.save(request);

        if(rows > 0){
            return "User Created Succefully";
        }
        return "User Creation Failed";
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();

    }

    public UserResponse getUserById(Long id) {

        try {

            User user = userRepository.findById(id);

            return UserMapper.toResponse(user);

        } catch (EmptyResultDataAccessException ex) {

            throw new ResourceNotFoundException(
                    "User not found with id " + id
            );

        }
    }


}
