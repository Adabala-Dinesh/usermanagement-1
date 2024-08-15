package com.nss.usermanagement.role.mapper;

import com.nss.usermanagement.role.entity.RolePermission;
import com.nss.usermanagement.role.model.ModulePermissionMapper;
import com.nss.usermanagement.role.model.OperationMapper;
import com.nss.usermanagement.role.model.RolePermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionMapper {
    @Autowired
    private ModulePermissionMapper modulePermissionMapper;

    private OperationMapper operationMapper;

    public RolePermissionDTO toDTO(RolePermission rolePermission) {
        RolePermissionDTO dto = new RolePermissionDTO();
        dto.setId(rolePermission.getId());
        dto.setRole(rolePermission.getRole());
        dto.setModulePermissions(modulePermissionMapper.toDTOList(rolePermission.getModulePermissions()));
        return dto;
    }

    public RolePermission toEntity(RolePermissionDTO dto) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(dto.getId());
        rolePermission.setRole(dto.getRole());
        rolePermission.setModulePermissions(modulePermissionMapper.toEntityList(dto.getModulePermissions()));
        return rolePermission;
    }
}
