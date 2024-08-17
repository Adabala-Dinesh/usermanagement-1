package com.nss.usermanagement.role.mapper;

import com.nss.usermanagement.role.entity.Module;
import com.nss.usermanagement.role.model.ModuleDTO;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleMapper {

    // Convert Entity to DTO
    public static ModuleDTO toDTO(Module module) {
        if (module == null) {
            return null;
        }
        ModuleDTO dto = new ModuleDTO();
        dto.setModuleId(module.getModuleId());
        dto.setModuleName(module.getModuleName());
        dto.setParentModuleId(module.getParentModule());
        if (module.getChildModules() != null) {
            dto.setChildModules(module.getChildModules().stream()
                    .map(ModuleMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    // Convert DTO to Entity
    public static Module toEntity(ModuleDTO dto) {
        if (dto == null) {
            return null;
        }
        Module module = new Module();
        module.setModuleId(dto.getModuleId());
        module.setModuleName(dto.getModuleName());
        module.setParentModule(dto.getParentModuleId());
        if (dto.getChildModules() != null) {
            module.setChildModules(dto.getChildModules().stream()
                    .map(ModuleMapper::toEntity)
                    .collect(Collectors.toList()));
        }
        return module;
    }
}
