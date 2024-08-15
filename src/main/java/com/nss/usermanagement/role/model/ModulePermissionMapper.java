package com.nss.usermanagement.role.model;

import com.nss.usermanagement.role.entity.ModulePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModulePermissionMapper {

    @Autowired
    private OperationMapper operationMapper;

    public ModulePermissionDTO toDTO(ModulePermission modulePermission) {
        if (modulePermission == null) {
            return null;
        }
        ModulePermissionDTO dto = new ModulePermissionDTO();
        dto.setId(modulePermission.getId());
        dto.setModuleId(modulePermission.getModule() != null ? modulePermission.getModule().getModuleId() : null);
        dto.setOperations(operationMapper.toDTOList(modulePermission.getOperations()));
        return dto;
    }

    public ModulePermission toEntity(ModulePermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        ModulePermission modulePermission = new ModulePermission();
        modulePermission.setId(dto.getId());
        // You would need to fetch the Module entity based on the moduleId if necessary
        // modulePermission.setModule(moduleRepository.findById(dto.getModuleId()).orElse(null));
        modulePermission.setOperations(operationMapper.toEntityList(dto.getOperations()));
        return modulePermission;
    }

    public List<ModulePermissionDTO> toDTOList(List<ModulePermission> modulePermissions) {
        if (modulePermissions == null) {
            return null;
        }
        return modulePermissions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ModulePermission> toEntityList(List<ModulePermissionDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
