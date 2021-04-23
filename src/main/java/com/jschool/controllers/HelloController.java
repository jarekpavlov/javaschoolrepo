package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HelloController {

    private EntityDaoImpl entityDaoImpl;
    @Autowired
    public HelloController(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl = entityDaoImpl;
    }

    @GetMapping(value = "/clients")
    public String hello(){
          Client client = new Client();
          client.setName("Guru");
        //  entityDaoImpl.saveEntity(client);
         // entityDaoImpl.delete(Client.class, 2L);
//          List<Client> clients = entityDaoImpl.entityList();
//          map.addAttribute("clients",clients);
//        System.out.println(entityDaoImpl.get(2L).getName());
//        Product product1 = new Product();
//        product1.setBrand("product1");
//        Product product2 = new Product();
//        product2.setBrand("product2");
//        Product product3 = new Product();
//        product3.setBrand("product3");
//
//        Order order1 = new Order();
//        order1.setClient(entityDaoImpl.get(1L));
//        Order order2 = new Order();
//        order2.setClient(entityDaoImpl.get(1L));
//
//        order1.getProductSet().add(product1);
//        order1.getProductSet().add(product3);
//        order2.getProductSet().add(product2);
//
//        product1.getOrderSet().add(order1);
//        product2.getOrderSet().add(order2);
//        product3.getOrderSet().add(order1);
//
//        entityDaoImpl.save(order1);
//        entityDaoImpl.save(order2);
        //  map.addAttribute("user", productId.toString());
        return "hello";
    }







}
