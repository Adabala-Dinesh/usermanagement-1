package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.Module;
import com.nss.usermanagement.role.exception.GeneralRunTimeException;
import com.nss.usermanagement.role.exception.ResourceNotFoundException;
import com.nss.usermanagement.role.mapper.ModuleMapper;
import com.nss.usermanagement.role.model.ModuleDTO;
import com.nss.usermanagement.role.request.ModuleRequest;
import com.nss.usermanagement.role.Responce.ModuleResponse;
import com.nss.usermanagement.role.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;
    public ModuleResponse createModule(ModuleRequest moduleRequest) {
        if (moduleRequest.getModuleDTO().getModuleName() == null || moduleRequest.getModuleDTO().getModuleName().isEmpty()) {
            throw new GeneralRunTimeException("Module name is empty");
        }

        Module module = ModuleMapper.toEntity(moduleRequest);
        module = moduleRepository.save(module);

        ModuleDTO moduleDTO = ModuleMapper.toDTO(module);

        List<ModuleDTO> moduleDTOList = List.of(moduleDTO);
        Page<ModuleDTO> moduleDTOPage = new PageImpl<>(moduleDTOList, PageRequest.of(0, 1), 1);

        return new ModuleResponse(moduleDTOPage);
    }
    public ModuleResponse getAllModules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Module> modulePage = moduleRepository.findAll(pageable);
        if (modulePage.isEmpty()) {
            throw new ResourceNotFoundException("Module details are not found");
        }


        Page<ModuleDTO> moduleDTOPage = modulePage.map(ModuleMapper::toDTO);

        return new ModuleResponse(moduleDTOPage);
    }

    public ModuleResponse getModuleById(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module details are not found"));

        ModuleDTO moduleDTO = ModuleMapper.toDTO(module);

        List<ModuleDTO> moduleDTOList = List.of(moduleDTO);
        Page<ModuleDTO> moduleDTOPage = new PageImpl<>(moduleDTOList, PageRequest.of(0, 1), 1);

        return new ModuleResponse(moduleDTOPage);
    }


    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
    public ModuleResponse updateModule(Long id, ModuleRequest moduleRequest) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found"));

        module.setModuleName(moduleRequest.getModuleDTO().getModuleName());
        module.setShortName(moduleRequest.getModuleDTO().getShortName());
        module.setParentModuleId(moduleRequest.getModuleDTO().getParentModuleId());

        module = ModuleMapper.prepareForUpdate(module);

        module = moduleRepository.save(module);


        ModuleDTO moduleDTO = ModuleMapper.toDTO(module);


        List<ModuleDTO> moduleDTOList = List.of(moduleDTO);
        Page<ModuleDTO> moduleDTOPage = new PageImpl<>(moduleDTOList, PageRequest.of(0, 1), 1);

        return new ModuleResponse(moduleDTOPage);
    }
    

}
