package com.ua.sutty.struts.service;

import com.ua.sutty.struts.domain.Role;
import com.ua.sutty.struts.repository.RoleDao;
import com.ua.sutty.struts.repository.impl.HibernateRoleDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HibernateRoleService {

    private RoleDao roleDao = new HibernateRoleDao();

    public void create(Role role) {
        if (role == null) {
            log.error("Role == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method create");
            roleDao.create(role);
        } catch (Exception e) {
            log.error("Error in time create role", e);
            throw e;
        }
    }

    public void update(Role role) {
        if (role == null) {
            log.error("Role == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method update");
            roleDao.update(role);
        } catch (Exception e) {
            log.error("Error in time update role", e);
            throw e;
        }
    }

    public void remove(Role role) {
        if (role == null) {
            log.error("Role == null", new NullPointerException());
            throw new NullPointerException();
        }
        if (role.getId() == null) {
            log.error("Id == null", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }
        try {
            log.trace("Call method update");
            roleDao.remove(role);
        } catch (Exception e) {
            log.error("Error in time remove role", e);
            throw e;
        }
    }

    public Role findByName(String name) {
        if (name == null) {
            log.error("Name == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method findByLogin");
            return roleDao.findByName(name);
        } catch (Exception e) {
            log.error("Error in time findByName", e);
            throw e;
        }
    }

    public Role findById(Long id) {
        if (id == null) {
            log.error("Role.id == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method findById");
            return roleDao.findById(id);
        } catch (Exception e) {
            log.error("Error in time findById", e);
            throw e;
        }
    }

}
