package com.nss.usermanagement.role.model;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class UserResponse {
    private Page<UserDTO> userPage;
    private int currentPage;
    private int totalItems;
    private int totalPages;

    public UserResponse(Page<UserDTO> userPage) {
        this.userPage = userPage;
        this.currentPage = userPage.getNumber();
        this.totalItems = userPage.getNumberOfElements();
        this.totalPages = userPage.getTotalPages();
    }
}
