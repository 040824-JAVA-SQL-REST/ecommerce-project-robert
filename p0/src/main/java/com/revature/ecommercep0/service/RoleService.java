package com.revature.ecommercep0.service;


import com.revature.ecommercep0.dao.RoleDao;
import com.revature.ecommercep0.model.Role;


public class RoleService {
    private RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    public Role save(Role obj) {
        return roleDao.save(obj);
    }
    public String getRoleIdByName(String name) {
        return roleDao.findAll().stream().filter(r -> r.getName().equals(name)).findFirst().get().getRole_id();
    }
    
}
