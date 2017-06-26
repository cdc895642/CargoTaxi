package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.RoleRepository;
import com.cargotaxi.mvc.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role> implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        setRepository(roleRepository);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRole(roleName);
    }
}
