package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.Responce.UserResponse;
import com.nss.usermanagement.role.entity.User;
import com.nss.usermanagement.role.mapper.UserMapper;
import com.nss.usermanagement.role.model.UserDTO;
import com.nss.usermanagement.role.repository.UserRepository;
import com.nss.usermanagement.role.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public UserDTO updateUser(Long userId, UserRequest userRequest) {
        // Validate the userRequest if necessary
        if (userRequest == null) {
            throw new IllegalArgumentException("UserRequest cannot be null");
        }

        // Fetch the existing user
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update fields using the mapper
        userMapper.updateUserEntity(existingUser, userRequest);

        // If the status is specified, update it
        if (userRequest.getStatus() != null) {
            existingUser.setStatus(userRequest.getStatus());
        }

        // Save the updated user entity
        User updatedUser = userRepository.save(existingUser);

        // Return the updated user as DTO
        return userMapper.toDTO(updatedUser);
    }

    // Retrieve a user by ID
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Check if user is active
        if (user.getStatus() == 0) { // Assuming 0 means inactive
            throw new RuntimeException("User is inactive and cannot be accessed.");
        }

        return userMapper.toDTO(user);
    }

    // Mark a user as inactive
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Set the user's status to inactive (0)
        user.setStatus(0);
        userRepository.save(user);
    }

    // Retrieve all users with pagination
    public UserResponse getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        // Optionally filter out inactive users
        Page<UserDTO> userDTOPage = userPage.getContent().stream()
                //.filter(user -> user.getStatus() == 1) // Assuming 1 means active
                .map(userMapper::toDTO)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, userPage.getTotalElements())
                ));

        return new UserResponse(userDTOPage);
    }
}

