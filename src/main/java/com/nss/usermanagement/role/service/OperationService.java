package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.Responce.OperationResponse;
import com.nss.usermanagement.role.entity.Operation;
import com.nss.usermanagement.role.exception.GeneralRunTimeException;
import com.nss.usermanagement.role.exception.ResourceNotFoundException;
import com.nss.usermanagement.role.mapper.OperationMapper;
import com.nss.usermanagement.role.model.OperationDTO;

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
        try {
            Operation operation = operationMapper.toEntity(operationDTO);
            operation = operationMapper.prepareForCreation(operation); // Set audit fields for creation
            Operation savedOperation = operationRepository.save(operation);
            return operationMapper.toDTO(savedOperation);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to create operation", ex);
        }
    }

    public OperationDTO getOperationById(Long id) {
        return operationRepository.findById(id)
                .map(operationMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Operation not found with id: " + id));
    }

    public OperationResponse getAllOperations(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Operation> operationPage = operationRepository.findAll(pageable);
            Page<OperationDTO> operationDTOPage = operationPage.map(operationMapper::toDTO);
            return new OperationResponse(operationDTOPage);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to retrieve operations", ex);
        }
    }

    public void deleteOperation(Long id) {
        if (!operationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Operation not found with id: " + id);
        }
        try {
            operationRepository.deleteById(id);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to delete operation with id: " + id, ex);
        }
    }

    public OperationDTO updateOperation(Long id, OperationDTO operationDTO) {
        Operation existingOperation = operationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation not found with id: " + id));

        try {
            existingOperation.setName(operationDTO.getOperationName());
            existingOperation = operationMapper.prepareForUpdate(existingOperation); // Set audit fields for update
            Operation updatedOperation = operationRepository.save(existingOperation);
            return operationMapper.toDTO(updatedOperation);
        } catch (Exception ex) {
            throw new GeneralRunTimeException("Failed to update operation with id: " + id, ex);
        }
    }
}
