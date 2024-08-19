package com.nss.usermanagement.role.model;

import lombok.Data;

@Data
public class OperationRequest extends AuditLog {
    private OperationDTO operationDTO = new OperationDTO();
}
