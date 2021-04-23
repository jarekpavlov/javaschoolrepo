package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    private EntityDaoImpl entityDaoImpl;

    @Autowired
    public OrderController(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl = entityDaoImpl;
    }

    @GetMapping(value = "/orders")
    public String createOrder(ModelMap map){
        List<Order> orders = entityDaoImpl.entityList(Order.class);
        map.addAttribute("orders", orders);
        return "orders";
    }
    @PostMapping(value = "/order/create")
    public String createOrder(@RequestParam int numberForOrder){
        System.out.println(numberForOrder);
        return "redirect:/products";
    }

}
