package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.Responce.UserResponse;
import com.nss.usermanagement.role.entity.User;
import com.nss.usermanagement.role.mapper.UserMapper;
import com.nss.usermanagement.role.model.UserDTO;
import com.nss.usermanagement.role.repository.UserRepository;
import com.nss.usermanagement.role.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Create or Save a new user
    public UserDTO createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    // Update an existing user
    public UserDTO updateUser(Long userId, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update fields using the mapper
            userMapper.updateUserEntity(existingUser, userRequest);

            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDTO(updatedUser);
        } else {
            // Handle user not found scenario
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    // Retrieve a user by ID
    public UserDTO getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return userMapper.toDTO(optionalUser.get());
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    // Delete a user by ID
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    // Retrieve all users with pagination
    public UserResponse getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        Page<UserDTO> userDTOPage = userPage.map(userMapper::toDTO);

        return new UserResponse(userDTOPage);
    }
}
