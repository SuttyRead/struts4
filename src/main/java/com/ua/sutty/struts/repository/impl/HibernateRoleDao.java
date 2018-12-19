package com.ua.sutty.struts.repository.impl;

import com.ua.sutty.struts.domain.Role;
import com.ua.sutty.struts.repository.RoleDao;
import com.ua.sutty.struts.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;

@Slf4j
public class HibernateRoleDao implements RoleDao {

    @Override
    public void create(Role role) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(role);
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
    public void update(Role role) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(role);
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
    public void remove(Role role) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.remove(role);
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
    public Role findByName(String name) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List roles = session.createQuery("FROM Role WHERE name = :name").setParameter("name", name).list();
            session.getTransaction().commit();
            return roles.isEmpty() ? null : (Role) roles.get(0);
        } catch (Exception e) {
            log.error("Error in time findByName role", e);
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
    public Role findById(Long id) throws RuntimeException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Role role = session.get(Role.class, id);
            session.getTransaction().commit();
            return role;
        } catch (Exception e) {
            log.error("Error in time findById role", e);
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
