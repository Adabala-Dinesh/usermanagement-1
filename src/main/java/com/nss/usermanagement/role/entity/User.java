package com.nss.usermanagement.role.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    @OneToOne
    @JoinColumn(name = "role_permission_id")
    private RolePermission rolePermission;
}
