package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.entity.RolePermission;
import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping
    public RolePermission createRolePermission(@RequestBody RolePermission rolePermission) {
        return rolePermissionService.createRolePermission(rolePermission);
    }

    @GetMapping
    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionService.getAllRolePermissions();
    }

    @GetMapping("/{id}")
    public RolePermissionDTO getRolePermissionById(@PathVariable Long id) {
        return rolePermissionService.getRolePermissionById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRolePermission(@PathVariable Long id) {
        rolePermissionService.deleteRolePermission(id);
    }
}
