package com.nss.usermanagement.role.model;

import lombok.Data;
import java.util.List;

@Data
public class ModuleDTO {

    private Long moduleId;
    private String moduleName;
    private Long parentModuleId;  // Use parentModuleId to avoid circular references
    private List<ModuleDTO> childModules;  // List of child modules

}
