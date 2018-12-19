package com.ua.sutty.struts.repository;

import com.ua.sutty.struts.domain.Role;

public interface RoleDao {

    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    Role findById(Long id);

}
