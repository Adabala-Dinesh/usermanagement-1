package com.nss.usermanagement.role.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String displayName;
    private String name;
    private int isActive;
    private String description;
    private List<PermissionDto> permissions;


}

