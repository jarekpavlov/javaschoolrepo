package com.jschool.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Class for the beginning and ending data base connection
public class EntityMenagerHandling {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void end() {
        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }

    public static void begin() {
        factory = Persistence.createEntityManagerFactory("MyDbConnection");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }
}
