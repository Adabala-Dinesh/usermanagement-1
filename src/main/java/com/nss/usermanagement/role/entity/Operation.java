package com.nss.usermanagement.role.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Operation extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
