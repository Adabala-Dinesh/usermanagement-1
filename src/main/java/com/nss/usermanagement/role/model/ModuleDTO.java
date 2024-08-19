package com.nss.usermanagement.role.model;

import lombok.Data;
import java.util.List;

@Data
public class ModuleDTO {

    private Long moduleId;
    private String moduleName;
    private Long parentModuleId;
    private String shortName;
    private List<ModuleDTO> childModules;

}
