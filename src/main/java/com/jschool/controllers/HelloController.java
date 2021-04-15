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
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class HelloController {

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String hello(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ClientUnit");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setAddress("Russia");
        client.setDateofbirth("12.12.1999");
        client.setName("John");
        client.setSurename("Johnson");
        client.setPhone("+7///");

        entityManager.persist(client);

        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
        return "hello";
    }
}
