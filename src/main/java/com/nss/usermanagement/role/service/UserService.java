package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.User;
import com.nss.usermanagement.role.model.UserDTO;
import com.nss.usermanagement.role.request.UserRequest;
import com.nss.usermanagement.role.Responce.UserResponse;
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
        User user = userMapper.toEntity(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(userMapper::toDTO).orElse(null);
    }

    public UserResponse getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").ascending());
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserDTO> userDTOPage = userPage.map(userMapper::toDTO);
        return new UserResponse(userDTOPage);
    }

    public UserDTO updateUser(Long id, UserRequest userRequest) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (!existingUserOpt.isPresent()) {
            throw new RuntimeException("User not found with ID " + id);
        }

        User existingUser = existingUserOpt.get();
        User updatedUser = userMapper.updateUserEntity(existingUser, userRequest);
        updatedUser = userRepository.save(updatedUser);
        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " does not exist");
        }
    }
}
