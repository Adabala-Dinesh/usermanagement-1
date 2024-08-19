package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.model.RolePermissionRequest;
import com.nss.usermanagement.role.model.RolePermissionResponse;
import com.nss.usermanagement.role.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping
    public ResponseEntity<RolePermissionDTO> createRolePermission(@RequestBody RolePermissionRequest rolePermissionRequest) {
        RolePermissionDTO createdRolePermission = rolePermissionService.createRolePermission(rolePermissionRequest);
        return ResponseEntity.ok(createdRolePermission);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolePermissionDTO> getRolePermissionById(@PathVariable Long id) {
        RolePermissionDTO rolePermissionDTO = rolePermissionService.getRolePermissionById(id);
        return ResponseEntity.ok(rolePermissionDTO);
    }

    @GetMapping
    public ResponseEntity<RolePermissionResponse> getAllRolePermissions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        RolePermissionResponse response = rolePermissionService.getAllRolePermissions(page, size);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable Long id) {
        rolePermissionService.deleteRolePermission(id);
        return ResponseEntity.noContent().build();
    }
}
