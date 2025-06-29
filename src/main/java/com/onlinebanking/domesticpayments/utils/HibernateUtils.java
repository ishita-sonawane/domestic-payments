package com.onlinebanking.domesticpayments.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtils {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtils.class);

    private static final SessionFactory sessionFactory;

    // Private constructor to prevent instantiation
    private HibernateUtils() {
        // Utility class
    }

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(
                    new RuntimeException("Failed to initialize Hibernate SessionFactory: " + ex.getMessage(), ex)
            );
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}