package com.nss.usermanagement.role.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    private String moduleName;

//    @ManyToOne
 //   @JoinColumn(name = "parent_id", nullable = true)
    @Column(name = "parent_Id")
    private Long parentModule;

    @OneToMany(mappedBy = "parentModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> childModules;

}
