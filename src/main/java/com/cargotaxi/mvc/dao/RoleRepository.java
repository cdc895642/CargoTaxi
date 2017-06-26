package com.cargotaxi.mvc.dao;

import com.cargotaxi.mvc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findRoleByRole(String role);
}
