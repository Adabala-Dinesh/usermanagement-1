package com.nss.usermanagement.role.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest extends AuditLog {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private Long rolePermissionId; // ID for RolePermission
    private String companyName;
    private Integer status;
    private String description;
}
