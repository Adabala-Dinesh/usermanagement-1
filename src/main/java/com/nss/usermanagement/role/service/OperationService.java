package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.Operation;
import com.nss.usermanagement.role.mapper.OperationMapper;
import com.nss.usermanagement.role.model.OperationDTO;
import com.nss.usermanagement.role.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationMapper operationMapper;

    public OperationDTO createOperation(OperationDTO operationDTO) {
        Operation operation = operationMapper.toEntity(operationDTO);
        Operation savedOperation = operationRepository.save(operation);
        return operationMapper.toDTO(savedOperation);
    }

    public OperationDTO getOperationById(Long id) {
        Optional<Operation> operation = operationRepository.findById(id);
        return operation.map(operationMapper::toDTO).orElse(null);
    }

    public List<OperationDTO> getAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operationMapper.toDTOList(operations);
    }

    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }
}
