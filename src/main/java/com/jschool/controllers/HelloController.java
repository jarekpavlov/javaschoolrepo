package com.jschool.controllers;

import com.jschool.DAO.ClientDaoImpl;
import com.jschool.domain.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {

    private ClientDaoImpl clientDao;
    @Autowired
    public HelloController(ClientDaoImpl clientDao){
        this.clientDao = clientDao;
    }

    @GetMapping(value = "/clients")
    public String hello(ModelMap map){

        Client client = new Client();
        client.setName("Ann");
        clientDao.save(client);
        clientDao.delete(4L);
        List<Client> clients = clientDao.clientList();
        map.addAttribute("clients",clients);
        System.out.println(clientDao.get(2L).getName());


        return "hello";
    }


}
