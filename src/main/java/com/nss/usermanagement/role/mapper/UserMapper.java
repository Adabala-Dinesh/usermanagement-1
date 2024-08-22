package com.nss.usermanagement.role.mapper;

import com.nss.usermanagement.role.entity.User;
import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.model.UserDTO;
import com.nss.usermanagement.role.request.UserRequest;
import com.nss.usermanagement.role.entity.RolePermission;
import com.nss.usermanagement.role.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCompanyName(user.getCompanyName());
        dto.setStatus(user.getStatus());
        dto.setDescription(user.getDescription());

        if (user.getRolePermission() != null) {
            RolePermissionDTO rolePermissionDTO = rolePermissionMapper.toDTO(user.getRolePermission());
            dto.setRolePermissionDTO(rolePermissionDTO);
        }

        return dto;
    }

    public User toEntity(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUserName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setCompanyName(userRequest.getCompanyName());
        user.setStatus(userRequest.getStatus());
        user.setDescription(userRequest.getDescription());

        if (userRequest.getRolePermissionId() != null) {
            // Fetch the RolePermission entity using the provided ID
            RolePermission rolePermission = rolePermissionRepository.findById(userRequest.getRolePermissionId())
                    .orElseThrow(() -> new RuntimeException("RolePermission not found with id " + userRequest.getRolePermissionId()));
            user.setRolePermission(rolePermission);
        }

        user = prepareForCreation(user);

        return user;
    }

    public User updateUserEntity(User existingUser, UserRequest userRequest) {
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setUsername(userRequest.getUserName());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        existingUser.setCompanyName(userRequest.getCompanyName());
        existingUser.setStatus(userRequest.getStatus());
        existingUser.setDescription(userRequest.getDescription());

        if (userRequest.getRolePermissionId() != null) {
            RolePermission rolePermission = rolePermissionRepository.findById(userRequest.getRolePermissionId())
                    .orElseThrow(() -> new RuntimeException("RolePermission not found with id " + userRequest.getRolePermissionId()));
            existingUser.setRolePermission(rolePermission);
        }

        existingUser = prepareForUpdate(existingUser);

        return existingUser;
    }

    public User prepareForCreation(User user) {
        String currentUser = getCurrentUser();
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setCreatedBy(currentUser);
        user.setUpdatedBy(currentUser);
        return user;
    }

    public User prepareForUpdate(User user) {
        String currentUser = getCurrentUser();
        user.setUpdatedOn(LocalDateTime.now());
        user.setUpdatedBy(currentUser);
        return user;
    }

    private String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
