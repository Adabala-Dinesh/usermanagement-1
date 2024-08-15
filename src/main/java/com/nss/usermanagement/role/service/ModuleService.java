package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.Module;
import com.nss.usermanagement.role.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
