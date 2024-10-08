package com.nss.usermanagement.role.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "module")
public class Module extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    private Long moduleId;

    @NotBlank(message = "Module name is required")
    @Column(name = "module_name", nullable = false, length = 100)
    private String moduleName;

    @Column(name = "short_name", length = 50)
    private String shortName;

    @NotNull(message = "Parent module ID is required")
    @Column(name = "parent_module_id", nullable = true)
    private Long parentModuleId;

    @OneToMany(mappedBy = "parentModuleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> childModules;
}
