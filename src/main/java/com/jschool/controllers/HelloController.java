package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    private EntityService entityService;
    @Autowired
    public HelloController(EntityService entityService){
        this.entityService = entityService;
    }

    @GetMapping(value = "/test")
    public String hello(){

//        Client client = entityService.deleteEntity(Client.class,3L);
//        System.out.println(client.getId());
        Client client = new Client();
        client.setName("Boba");
        client.setEmail("@t-systems");
        client.setId(4L);
        entityService.updateEntity(client);
        return "users";
    }
    @GetMapping(value = "")
    public String startPage(){
        return "startPage";
    }








}
