package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.model.OperationDTO;
import com.nss.usermanagement.role.request.OperationRequest;
import com.nss.usermanagement.role.Responce.OperationResponse;
import com.nss.usermanagement.role.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@RequestBody OperationRequest operationRequest) {
        OperationDTO createdOperation = operationService.createOperation(operationRequest.getOperationDTO());
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
    public ResponseEntity<OperationResponse> getAllOperations(@RequestParam int page, @RequestParam int size) {
        OperationResponse response = operationService.getAllOperations(page, size);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        operationService.deleteOperation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable Long id, @RequestBody OperationRequest operationRequest) {
        OperationDTO updatedOperation = operationService.updateOperation(id, operationRequest.getOperationDTO());
        if (updatedOperation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOperation);
    }
}
