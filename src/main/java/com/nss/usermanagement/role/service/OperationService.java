package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.Operation;
import com.nss.usermanagement.role.mapper.OperationMapper;
import com.nss.usermanagement.role.model.OperationDTO;
import com.nss.usermanagement.role.Responce.OperationResponse;
import com.nss.usermanagement.role.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationMapper operationMapper;

    public OperationDTO createOperation(OperationDTO operationDTO) {
        Operation operation = operationMapper.toEntity(operationDTO);
        operation = operationMapper.prepareForCreation(operation); // Set audit fields for creation
        Operation savedOperation = operationRepository.save(operation);
        return operationMapper.toDTO(savedOperation);
    }

    public OperationDTO getOperationById(Long id) {
        Optional<Operation> operation = operationRepository.findById(id);
        return operation.map(operationMapper::toDTO).orElse(null);
    }

    public OperationResponse getAllOperations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Operation> operationPage = operationRepository.findAll(pageable);
        Page<OperationDTO> operationDTOPage = operationPage.map(operationMapper::toDTO);
        return new OperationResponse(operationDTOPage);
    }

    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }

    public OperationDTO updateOperation(Long id, OperationDTO operationDTO) {
        Optional<Operation> existingOperationOpt = operationRepository.findById(id);
        if (!existingOperationOpt.isPresent()) {
            return null;
        }

        Operation existingOperation = existingOperationOpt.get();
        existingOperation.setName(operationDTO.getOperationName());
        existingOperation = operationMapper.prepareForUpdate(existingOperation); // Set audit fields for update

        Operation updatedOperation = operationRepository.save(existingOperation);
        return operationMapper.toDTO(updatedOperation);
    }
}
