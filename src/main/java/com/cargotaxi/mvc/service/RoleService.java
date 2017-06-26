package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.model.Role;

public interface RoleService extends AbstractService<Role>{
    public Role findRoleByRoleName(String roleName);
}
