package com.jschool.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class EntityDao {

    private SessionFactory factory;
    private Session currentSession;

    public EntityDao(SessionFactory factory) {
        this.factory = factory;
    }

    public <T> T saveEntity(T entity) {
        currentSession.saveOrUpdate(entity);
        return entity;
    }

    public <T> T getEntity(Class<T> type, Long id) {
        T entity = currentSession.get(type, id);
        return entity;
    }

    public <T> T updateEntity(T entity) {
        currentSession.saveOrUpdate(entity);
        return entity;
    }

    public <T> T deleteEntity(Class<T> type, Long id) {
        T reference = getEntity(type, id);
        currentSession.delete(reference);
        return reference;
    }

    public <T> List<T> entityList(Class<T> type) {
        Query query = currentSession.createQuery("select c from " + type.getName() + " c");
        return (List<T>) query.getResultList();
    }

    public <T> List<T> getEntityByEmail(Class<T> type, String email) {
        Query query = currentSession.createQuery("select c from " + type.getName() + " c where c.email='" + email + "'");
        return (List<T>) query.getResultList();
    }

    public Session openCurrentSession() {
        currentSession = factory.getCurrentSession();
        return currentSession;
    }

}
