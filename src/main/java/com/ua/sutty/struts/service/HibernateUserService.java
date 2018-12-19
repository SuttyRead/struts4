package com.ua.sutty.struts.service;

import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.repository.UserDao;
import com.ua.sutty.struts.repository.impl.HibernateUserDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HibernateUserService {

    private UserDao userDao = new HibernateUserDao();

    public void create(User user) {
        if (user == null) {
            log.error("Role == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method create");
            userDao.create(user);
        } catch (Exception e) {
            log.error("Error in time create user", e);
            throw e;
        }
    }

    public void update(User user) {
        if (user == null) {
            log.error("Role == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method update");
            userDao.update(user);
        } catch (Exception e) {
            log.error("Error in time create user", e);
            throw e;
        }
    }

    public void remove(User user) {
        if (user == null) {
            log.error("User == null", new NullPointerException());
            throw new NullPointerException();
        }
        if (user.getId() == null) {
            log.error("Id == null", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }
        try {
            log.trace("Call method remove");
            userDao.remove(user);
        } catch (Exception e) {
            log.error("Error in time create user", e);
            throw e;
        }
    }

    public List findAll() {
        try {
            log.trace("Call method findAll");
            return userDao.findAll();
        } catch (Exception e) {
            log.error("Error in time findAll", e);
            throw e;
        }
    }

    public User findByLogin(String login) {
        if (login == null) {
            log.error("login == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method findByLogin");
            return userDao.findByLogin(login);
        } catch (Exception e) {
            log.error("Error in time findByLogin", e);
            throw e;
        }
    }

    public User findByEmail(String email) {
        if (email == null) {
            log.error("Role == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method findByEmail");
            return userDao.findByEmail(email);
        } catch (Exception e) {
            log.error("Error in time findByEmail", e);
            throw e;
        }
    }

    public User findById(Long id) {
        if (id == null) {
            log.error("User.id == null", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            log.trace("Call method findById");
            return userDao.findById(id);
        } catch (Exception e) {
            log.error("Error in time findById", e);
            throw e;
        }
    }

}
