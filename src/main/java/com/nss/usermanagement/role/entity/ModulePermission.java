package com.nss.usermanagement.role.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ModulePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations;

    @ElementCollection
    @CollectionTable(name = "module_permission_operations", joinColumns = @JoinColumn(name = "module_permission_id"))
    @Column(name = "operation_name")
    private List<String> operationNames;


}
