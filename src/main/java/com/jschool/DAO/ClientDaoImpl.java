package com.jschool.DAO;

import com.jschool.domain.Client;

import javax.persistence.Query;
import java.util.List;

public class ClientDaoImpl implements ClientDao{

    @Override
    public void save(Client client) {
    EntityMenagerHandling.begin();
    EntityMenagerHandling.getEntityManager().persist(client);// "persist" is used to create a new instance
    EntityMenagerHandling.end();
    }

    @Override
    public Client get(Long id) {
        EntityMenagerHandling.begin();
        Client client = EntityMenagerHandling.getEntityManager().find(Client.class, id);//"find" is used to find and give an object from data base
        EntityMenagerHandling.end();

        return client;
    }

    @Override
    public void update(Client client) {
        EntityMenagerHandling.begin();
        EntityMenagerHandling.getEntityManager().merge(client); //"merge" is used to update a new instance
        EntityMenagerHandling.end();

    }

    @Override
    public void delete(Long id) {
        EntityMenagerHandling.begin();
        Client reference = EntityMenagerHandling.getEntityManager().getReference(Client.class, id);
        EntityMenagerHandling.getEntityManager().remove(reference);//"remove" is used to delete instance using reference that was obtained earlier
        EntityMenagerHandling.end();
    }

    @Override
    public List<Client> clientList() {
        EntityMenagerHandling.begin();
        Query query = EntityMenagerHandling.getEntityManager().createQuery("select c from Client c"); //"createQuery is using to retrieve information using custom query "
        List<Client> resultList = query.getResultList();
        EntityMenagerHandling.begin();
        return  resultList;
    }
}
