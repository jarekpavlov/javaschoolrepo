package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    private EntityDaoImpl entityDaoImpl;
    @Autowired
    public HelloController(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl = entityDaoImpl;
    }

    @GetMapping(value = "/test")
    public String hello(){

//        Product product1 = entityDaoImpl.getEntity(Product.class,1L);
//        Product product2 = entityDaoImpl.getEntity(Product.class,2L);
//
//
//        Order order1 = new Order();
//        order1.setClient(entityDaoImpl.getEntity(Client.class,1L));
//        Order order2 = new Order();
//        order2.setClient(entityDaoImpl.getEntity(Client.class,1L));
//
//        order1.getProductSet().add(product1);
//        order2.getProductSet().add(product2);
//
//        product1.getOrderSet().add(order1);
//        product2.getOrderSet().add(order2);
//
//        entityDaoImpl.saveEntity(order1);
//        entityDaoImpl.saveEntity(order2);
        return "users";
    }
    @GetMapping(value = "")
    public String startPage(){
        return "startPage";
    }








}
