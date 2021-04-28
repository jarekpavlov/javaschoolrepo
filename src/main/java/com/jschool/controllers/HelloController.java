package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
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

        Order order = new Order();
        ProductsInOrder productsInOrder1= new ProductsInOrder();
        ProductsInOrder productsInOrder2= new ProductsInOrder();
        Client client = entityService.getEntity(Client.class,1L);
        Product product1=entityService.getEntity(Product.class,1L);
        Product product2=entityService.getEntity(Product.class,2L);
        order.setClient(client);
        productsInOrder1.setProduct(product1);
        productsInOrder2.setProduct(product2);
        productsInOrder1.setOrder(order);
        productsInOrder2.setOrder(order);
        product1.getProductsInOrderSet().add(productsInOrder1);
        product2.getProductsInOrderSet().add(productsInOrder2);
        order.getProductsInOrderSet().add(productsInOrder1);
        order.getProductsInOrderSet().add(productsInOrder2);
        entityService.saveEntity(order);



        return "users";
    }
    @GetMapping(value = "")
    public String startPage(){
        return "startPage";
    }








}
