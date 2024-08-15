package com.nss.usermanagement.role.controller;

import com.nss.usermanagement.role.entity.Module;
import com.nss.usermanagement.role.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        return moduleService.createModule(module);
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }
}
