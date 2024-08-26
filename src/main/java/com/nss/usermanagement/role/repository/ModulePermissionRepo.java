package com.nss.usermanagement.role.repository;

import com.nss.usermanagement.role.entity.ModulePermission;
import com.nss.usermanagement.role.model.ModulePermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModulePermissionRepo extends JpaRepository<ModulePermission,Long> {


}
