package com.jschool.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class EntityDaoImpl  {

    private SessionFactory factory;
    private  Session currentSession;


    public EntityDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }


    public <T> T saveEntity(T entity) {
        currentSession.saveOrUpdate(entity);// "saveOrUpdate" is used to create a new instance
        return  entity;
    }
    public <T> T getEntity( Class<T> type,Long id) {
        T entity = currentSession.get(type, id);//"get" is used to find and give an object from data base
        return entity;
    }

    public<T> T updateEntity(T entity) {
        currentSession.saveOrUpdate(entity); //"saveOrUpdate" is used to update a new instance
        return entity;
    }

    public <T> T deleteEntity(Class<T> type, Long id) {
        T reference = getEntity(type, id);
        currentSession.delete(reference);//"delete" is used to delete instance using reference that was obtained earlier
        return reference;
    }

    public <T> List<T> entityList(Class<T> type) {
        Query query = currentSession.createQuery("select c from "+type.getName()+ " c"); //"createQuery is using to retrieve information using custom query "
        List<T> resultList = query.getResultList();
        return  resultList;
    }
    public Session openCurrentSession() {
        currentSession = factory.getCurrentSession();
        return currentSession;
    }

}
