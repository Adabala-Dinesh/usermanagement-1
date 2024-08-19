package com.nss.usermanagement.role.model;

import com.nss.usermanagement.role.entity.RolePermission;
import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private RolePermissionDTO rolePermissionDTO;
    private String companyName;
    private int status;
    private String description;

}
