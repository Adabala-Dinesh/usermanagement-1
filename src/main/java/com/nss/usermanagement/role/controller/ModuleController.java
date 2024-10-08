package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.model.ModuleRequest;
import com.nss.usermanagement.role.model.ModuleResponse;
import com.nss.usermanagement.role.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public ResponseEntity<ModuleResponse> createModule(@RequestBody ModuleRequest moduleRequest) {
        ModuleResponse response = moduleService.createModule(moduleRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ModuleResponse> getAllModules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ModuleResponse response = moduleService.getAllModules(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable Long id) {
        ModuleResponse response = moduleService.getModuleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponse> updateModule(
            @PathVariable Long id, @RequestBody ModuleRequest moduleRequest) {
        ModuleResponse response = moduleService.updateModule(id, moduleRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
