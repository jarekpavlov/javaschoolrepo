package com.jschool.DAO;

import com.jschool.domain.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import java.util.List;

public class ClientDaoImpl implements ClientDao{

    private static SessionFactory factory;
    private static Session session;
    private static Transaction transaction;

    @Autowired
    public ClientDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Client client) {
    begin();
    session.persist(client);// "persist" is used to create a new instance
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
    public void update(Client client) {
        begin();
        session.merge(client); //"merge" is used to update a new instance
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
    public List<Client> clientList() {
        begin();
        Query query = session.createQuery("select c from Client c"); //"createQuery is using to retrieve information using custom query "
        List<Client> resultList = query.getResultList();
        end();
        return  resultList;
    }
    public static void begin(){
        session = factory.openSession();
        transaction = session.beginTransaction();
    }
    public static void end(){
        transaction.commit();
        session.close();
        factory.close();
    }
}
