package com.nss.usermanagement.role.mapper;

import com.nss.usermanagement.role.entity.ModulePermission;
import com.nss.usermanagement.role.entity.RolePermission;
import com.nss.usermanagement.role.model.ModulePermissionDTO;
import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.model.RolePermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RolePermissionMapper {

    @Autowired
    private ModulePermissionMapper modulePermissionMapper;

    public RolePermissionDTO toDTO(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }

        RolePermissionDTO dto = new RolePermissionDTO();
        dto.setId(rolePermission.getId());
        dto.setRole(rolePermission.getRole());
        dto.setStatus(rolePermission.getStatus());
        dto.setDescription(rolePermission.getDescription());

        if (rolePermission.getModulePermissions() != null) {
            List<ModulePermissionDTO> modulePermissionsDTO = rolePermission.getModulePermissions().stream()
                    .map(modulePermissionMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setModulePermissions(modulePermissionsDTO);
        }

        return dto;
    }

    public RolePermission toEntity(RolePermissionRequest rolePermissionRequest) {
        if (rolePermissionRequest == null) {
            return null;
        }

        RolePermissionDTO dto = rolePermissionRequest.getRolePermissionDTO();
        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(dto.getId());
        rolePermission.setRole(dto.getRole());
        rolePermission.setStatus(dto.getStatus());
        rolePermission.setDescription(dto.getDescription());

        if (dto.getModulePermissions() != null) {
            List<ModulePermission> modulePermissions = dto.getModulePermissions().stream()
                    .map(modulePermissionMapper::toEntity)
                    .collect(Collectors.toList());
            rolePermission.setModulePermissions(modulePermissions);
        }

        rolePermission = prepareForCreation(rolePermission);

        return rolePermission;
    }

    public RolePermission prepareForCreation(RolePermission rolePermission) {
        String currentUser = getCurrentUser();
        rolePermission.setCreatedOn(LocalDateTime.now());
        rolePermission.setUpdatedOn(LocalDateTime.now());
        rolePermission.setCreatedBy(currentUser);
        rolePermission.setUpdatedBy(currentUser);
        return rolePermission;
    }

    public RolePermission prepareForUpdate(RolePermission rolePermission) {
        String currentUser = getCurrentUser();
        rolePermission.setUpdatedOn(LocalDateTime.now());
        rolePermission.setUpdatedBy(currentUser);
        return rolePermission;
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
