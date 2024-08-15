package com.nss.usermanagement.role.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class RolePermissionDTO {
    private Long id;
    private String role;
    private List<ModulePermissionDTO> modulePermissions;

    // Getters and Setters
}
