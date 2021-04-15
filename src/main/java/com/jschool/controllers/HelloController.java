package com.jschool.controllers;

import com.jschool.domain.Client;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class HelloController {

     static EntityManagerFactory factory;
     static EntityManager entityManager;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String hello(){
        begin();
        //create();
        //update((long)1);
        //read((long)1);
        delete((long)1);
        //customQ("select c from Client c where c.id in (1,2)");
        end();

        return "hello";
    }

    private void create() {

        Client client = new Client();
        client.setAddress("Russia");
        client.setDateofbirth("12.12.1999");
        client.setName("John");
        client.setSurename("Johnson");
        client.setPhone("+7///");

        entityManager.persist(client);// "persist" is using to create a new instance

    }
    private void read(Long id){
        Client client = entityManager.find(Client.class, id);//"find"
        System.out.println(client.getName()+" "+client.getDateofbirth());
    }
    private void update(Long id){
        Client client = new Client();
        client.setId(id);
        client.setAddress("USA");
        client.setDateofbirth("12.12.1988");
        client.setName("John");
        client.setSurename("Johnson");
        client.setPhone("+54///");
        entityManager.merge(client); //"merge" is using to update a new instance
    }
    private void delete(Long id){
        Client reference = entityManager.getReference(Client.class, id);
        entityManager.remove(reference);//"remove" is using to delete instance using reference that was obtained earlier

    }
    private void customQ(String jpql){
        Query query = entityManager.createQuery(jpql); //"createQuery is using to retrieve information using custom query "
        List<Client> resultList = query.getResultList();
        for (Client client : resultList){
            System.out.println(client.getName()+" "+client.getDateofbirth());
        }
    }

    private static void end() {
        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }

    private static void begin() {
        factory = Persistence.createEntityManagerFactory("ClientUnit");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }
}
