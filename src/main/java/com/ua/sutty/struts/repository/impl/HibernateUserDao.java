package com.ua.sutty.struts.repository.impl;

import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.repository.UserDao;
import com.ua.sutty.struts.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;

@Slf4j
public class HibernateUserDao implements UserDao {

    @Override
    public void create(User user) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error in time save user", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(User user) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error in time update user", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(User user) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error in time remove user", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List findAll() throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List users = session.createQuery("FROM User").list();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            log.error("Error in time findAll user", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User findByLogin(String login) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List users = session.createQuery("FROM User WHERE login = :login").setParameter("login", login).list();
            session.getTransaction().commit();
            return users.isEmpty() ? null : (User) users.get(0);
        } catch (Exception e) {
            log.error("Error in time findByLogin", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User findByEmail(String email) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List users = session.createQuery("FROM User WHERE email = :email").setParameter("email", email).list();
            session.getTransaction().commit();
            return users.isEmpty() ? null : (User) users.get(0);
        } catch (Exception e) {
            log.error("Error in time findByEmail", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User findById(Long id) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            log.error("Error in time findById", e);
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
