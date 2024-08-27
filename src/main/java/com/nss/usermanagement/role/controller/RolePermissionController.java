package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.request.RolePermissionRequest;
import com.nss.usermanagement.role.Responce.RolePermissionResponse;
import com.nss.usermanagement.role.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<RolePermissionDTO> updateRolePermission(
            @PathVariable("id") Long id,
            @RequestBody RolePermissionRequest rolePermissionRequest) {

        RolePermissionDTO updatedRolePermissionDTO = rolePermissionService.updateRolePermission(id, rolePermissionRequest);

        if (updatedRolePermissionDTO != null) {
            return new ResponseEntity<>(updatedRolePermissionDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRolePermission(@PathVariable Long id) {
        try {
            rolePermissionService.deleteRolePermission(id);
            return ResponseEntity.ok("RolePermission deleted successfully.");
        } catch (Exception e) {
            // Optional: Handle specific exceptions, e.g., if the role permission is not found
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete RolePermission with ID " + id);
        }
    }

}
