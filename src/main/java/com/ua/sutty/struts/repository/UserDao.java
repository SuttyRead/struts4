package com.ua.sutty.struts.repository;

import com.ua.sutty.struts.domain.User;

import java.util.List;

public interface UserDao {

    void create(User user);

    void update(User role);

    void remove(User role);

    List findAll();

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Long id);

}
