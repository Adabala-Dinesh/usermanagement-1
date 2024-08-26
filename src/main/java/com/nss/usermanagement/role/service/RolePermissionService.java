package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.Responce.RolePermissionResponse;
import com.nss.usermanagement.role.entity.ModulePermission;
import com.nss.usermanagement.role.entity.RolePermission;
import com.nss.usermanagement.role.entity.Operation;
import com.nss.usermanagement.role.exception.ResourceNotFoundException;
import com.nss.usermanagement.role.exception.GeneralRunTimeException;
import com.nss.usermanagement.role.mapper.RolePermissionMapper;
import com.nss.usermanagement.role.mapper.ModulePermissionMapper;
import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.request.RolePermissionRequest;

import com.nss.usermanagement.role.repository.RolePermissionRepository;
import com.nss.usermanagement.role.repository.ModulePermissionRepo;
import com.nss.usermanagement.role.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private ModulePermissionRepo modulePermissionRepo;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private ModulePermissionMapper modulePermissionMapper;

    @Autowired
    private OperationRepository operationRepository;

    public RolePermissionDTO createRolePermission(RolePermissionRequest rolePermissionRequest) {
        try {
            RolePermission rolePermission = rolePermissionMapper.toEntity(rolePermissionRequest);
            List<ModulePermission> modulePermissions = rolePermission.getModulePermissions().stream()
                    .map(modulePermission -> {
                        // Ensure all operations are attached to the session
                        List<Operation> attachedOperations = modulePermission.getOperations().stream()
                                .map(operation -> operationRepository.findById(operation.getId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Operation with ID " + operation.getId() + " not found")))
                                .collect(Collectors.toList());
                        modulePermission.setOperations(attachedOperations);
                        return modulePermissionRepo.save(modulePermission);
                    })
                    .collect(Collectors.toList());
            rolePermission.setModulePermissions(modulePermissions);
            RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
            return rolePermissionMapper.toDTO(savedRolePermission);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to create RolePermission", ex);
        }
    }

    public RolePermissionDTO getRolePermissionById(Long id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RolePermission not found with ID " + id));
        return rolePermissionMapper.toDTO(rolePermission);
    }

    public RolePermissionResponse getAllRolePermissions(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<RolePermission> rolePermissionsPage = rolePermissionRepository.findAll(pageable);

            // Map RolePermission to RolePermissionDTO
            Page<RolePermissionDTO> rolePermissionDTOPage = rolePermissionsPage.map(rolePermissionMapper::toDTO);

            // Return a RolePermissionResponse with the DTOs
            return new RolePermissionResponse(rolePermissionDTOPage);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to retrieve RolePermissions", ex);
        }
    }

    public RolePermissionDTO updateRolePermission(Long id, RolePermissionRequest rolePermissionRequest) {
        RolePermission existingRolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RolePermission not found with ID " + id));

        try {
            RolePermission updatedRolePermission = rolePermissionMapper.updateEntity(existingRolePermission, rolePermissionRequest);
            RolePermission savedRolePermission = rolePermissionRepository.save(updatedRolePermission);
            return rolePermissionMapper.toDTO(savedRolePermission);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to update RolePermission with ID " + id, ex);
        }
    }

    public void deleteRolePermission(Long id) {
        if (rolePermissionRepository.existsById(id)) {
            try {
                rolePermissionRepository.deleteById(id);
            } catch (Exception ex) {
                throw new GeneralRunTimeException("Failed to delete RolePermission with ID " + id, ex);
            }
        } else {
            throw new ResourceNotFoundException("RolePermission with ID " + id + " does not exist");
        }
    }
}
