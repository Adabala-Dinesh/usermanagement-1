package com.nss.usermanagement.role.model;

import com.nss.usermanagement.role.model.AuditLog;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RolePermissionRequest extends AuditLog {
    private RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
}
