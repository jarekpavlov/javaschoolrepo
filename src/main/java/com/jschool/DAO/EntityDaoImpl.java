package com.jschool.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EntityDaoImpl  {

    private static SessionFactory factory;
    private static Session session;
    private static Transaction transaction;


    public EntityDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public <T> T saveEntity(T entity) {
        begin();
        session.persist(entity);// "persist" is used to create a new instance
        end();
        return  entity;
    }
    public <T> T getEntity( Class<T> type,Long id) {
        begin();
        T entity = session.find( type, id);//"find" is used to find and give an object from data base
        end();
        return entity;
    }

    public<T> T update(T entity) {
        begin();
        session.merge(entity); //"merge" is used to update a new instance
        end();
        return entity;
    }

    public <T> T delete( Class<T> type,Long id) {
        begin();
        T reference = session.getReference(type, id);
        session.remove(reference);//"remove" is used to delete instance using reference that was obtained earlier
        end();
        return reference;
    }

    public <T> List<T> entityList(Class<T> type) {
        begin();
        Query query = session.createQuery("select c from "+type.getName()+ " c"); //"createQuery is using to retrieve information using custom query "
        List<T> resultList = query.getResultList();
        end();
        return  resultList;
    }
    public static void begin(){
        session = factory.openSession();
        transaction = session.beginTransaction();
    }
    public static void end(){
        session.getTransaction().commit();
        session.close();
    }
}
