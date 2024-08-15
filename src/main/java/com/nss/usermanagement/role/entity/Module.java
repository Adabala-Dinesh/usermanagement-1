package com.nss.usermanagement.role.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    private String moduleName;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Module parentModule;  // Self-referencing relationship

    @OneToMany(mappedBy = "parentModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> childModules;  // List of child modules

    // Getters and Setters

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Module getParentModule() {
        return parentModule;
    }

    public void setParentModule(Module parentModule) {
        this.parentModule = parentModule;
    }

    public List<Module> getChildModules() {
        return childModules;
    }

    public void setChildModules(List<Module> childModules) {
        this.childModules = childModules;
    }
}
