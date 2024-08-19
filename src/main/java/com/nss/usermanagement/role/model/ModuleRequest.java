package com.nss.usermanagement.role.model;

import lombok.Data;

@Data
public class ModuleRequest extends AuditLog {
    private ModuleDTO moduleDTO = new ModuleDTO();
}
