package com.nss.usermanagement.role.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class ModulePermissionDTO {
    private Long id;
    private Long moduleId;
    private List<OperationDTO> operations;

    // Getters and Setters
}
