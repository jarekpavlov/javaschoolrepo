package com.jschool.DAO;

import com.jschool.domain.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import java.util.List;

public class EntityDaoImpl<T> implements EntityDao<T> {

    private static SessionFactory factory;
    private static Session session;
    private static Transaction transaction;
    private Class<T> processedClass;
    public void setProcessedClass(Class<T> processedClass){
        this.processedClass=processedClass;
    }

    @Autowired
    public EntityDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(T entity) {
    begin();
    session.persist(entity);// "persist" is used to create a new instance
    end();
    }

    @Override
    public Client get(Long id) {
        begin();
        Client client = session.find(Client.class, id);//"find" is used to find and give an object from data base
        end();

        return client;
    }

    @Override
    public void update(T entity) {
        begin();
        session.merge(entity); //"merge" is used to update a new instance
        end();

    }

    @Override
    public void delete(Long id) {
        begin();
        Client reference = session.getReference(Client.class, id);
        session.remove(reference);//"remove" is used to delete instance using reference that was obtained earlier
        end();
    }

    @Override
    public List<T> clientList() {
        begin();
        Query query = session.createQuery("select c from "+processedClass.getName()+ " c"); //"createQuery is using to retrieve information using custom query "
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
