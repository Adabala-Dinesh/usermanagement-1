package com.nss.usermanagement.role.Responce;

import com.nss.usermanagement.role.model.OperationDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class OperationResponse {
    private Page<OperationDTO> operationPage;
    private int currentPage;
    private int totalItems;
    private int totalPages;

    public OperationResponse(Page<OperationDTO> operationPage) {
        this.operationPage = operationPage;
        this.currentPage = operationPage.getNumber();
        this.totalItems = operationPage.getNumberOfElements();
        this.totalPages = operationPage.getTotalPages();
    }
}
