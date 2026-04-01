package com.filemasterapp.FilemasterApp.Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase que gestiona la sesión de Hibernate para el proyecto.
 * <p>
 * Proporciona un Singleton para la SessionFactory de Hibernate,
 * asegurando que solo exista una instancia en todo el sistema.
 * </p>
 */
public class HibernateSessionHandler {

    /**
     * SessionFactory de Hibernate.
     */
    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa la SessionFactory.
     * Si no se proporciona una SessionFactory, se crea una nueva
     * configurando hibernate.cfg.xml.
     */
    public HibernateSessionHandler() {
        this.sessionFactory = null;
    }

    /**
     * Obtiene la SessionFactory de Hibernate.
     * <p>
     * Si la SessionFactory no ha sido inicializada, crea una nueva
     * configurando hibernate.cfg.xml.
     * </p>
     *
     * @return la SessionFactory de Hibernate
     */
    public SessionFactory getSessionFactory() {
        if (this.sessionFactory == null) {
            this.sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Establece la SessionFactory de Hibernate.
     *
     * @param sessionFactory la SessionFactory a establecer
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
