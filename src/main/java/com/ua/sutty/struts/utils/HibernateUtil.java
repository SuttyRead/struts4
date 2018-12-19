package com.ua.sutty.struts.utils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateUtil {

    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException e) {
            log.error("Error in time load SessionFactory", e);
            throw e;
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

}
