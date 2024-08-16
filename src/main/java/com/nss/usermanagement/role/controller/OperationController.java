package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.model.OperationDTO;
import com.nss.usermanagement.role.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@RequestBody OperationDTO operationDTO) {
        OperationDTO createdOperation = operationService.createOperation(operationDTO);
        return ResponseEntity.ok(createdOperation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperationById(@PathVariable Long id) {
        OperationDTO operationDTO = operationService.getOperationById(id);
        if (operationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(operationDTO);
    }

    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operations = operationService.getAllOperations();
        return ResponseEntity.ok(operations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        operationService.deleteOperation(id);
        return ResponseEntity.noContent().build();
    }
}
