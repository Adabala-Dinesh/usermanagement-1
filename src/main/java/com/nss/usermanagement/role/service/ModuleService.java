package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.Module;
import com.nss.usermanagement.role.exception.GeneralRunTimeException;
import com.nss.usermanagement.role.exception.ResourceNotFoundException;
import com.nss.usermanagement.role.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;


    public Module createModule(Module module) {
        if(module.getModuleName() == null || module.getModuleName().isEmpty()) {
            throw new GeneralRunTimeException("Module name is empty");
        }
       return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
         return Optional.of(moduleRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("module deatils are not found "));
    }

    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("module deatils are not found "));
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
