package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.Responce.UserResponse;
import com.nss.usermanagement.role.entity.User;
import com.nss.usermanagement.role.exception.GeneralRunTimeException;
import com.nss.usermanagement.role.exception.ResourceNotFoundException;
import com.nss.usermanagement.role.model.UserDTO;
import com.nss.usermanagement.role.request.UserRequest;

import com.nss.usermanagement.role.repository.UserRepository;
import com.nss.usermanagement.role.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserRequest userRequest) {
        try {
            User user = userMapper.toEntity(userRequest);
            User savedUser = userRepository.save(user);
            return userMapper.toDTO(savedUser);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to create user", ex);
        }
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));
    }

    public UserResponse getAllUsers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("userId").ascending());
            Page<User> userPage = userRepository.findAll(pageable);
            Page<UserDTO> userDTOPage = userPage.map(userMapper::toDTO);
            return new UserResponse(userDTOPage);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to retrieve users", ex);
        }
    }

    public UserDTO updateUser(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));

        try {
            User updatedUser = userMapper.updateUserEntity(existingUser, userRequest);
            updatedUser = userRepository.save(updatedUser);
            return userMapper.toDTO(updatedUser);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to update user with ID " + id, ex);
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);
            } catch (Exception ex) {
                throw new GeneralRunTimeException("Failed to delete user with ID " + id, ex);
            }
        } else {
            throw new ResourceNotFoundException("User with ID " + id + " does not exist");
        }
    }
}
