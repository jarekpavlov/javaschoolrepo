package com.jschool.controllers;

import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import com.jschool.service.EntityService;
import com.jschool.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {

    private EntityService entityService;
    private OrderService orderService;

    @Autowired
    public OrderController(EntityService entityService, OrderService orderService) {
        this.entityService = entityService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public String createOrder(ModelMap map) {
        List<Order> orders = entityService.entityList(Order.class);
        map.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping(value = "/order/addtocart")
    public String addToCart(@RequestParam int numberForOrder, HttpServletRequest request) {

        orderService.addToCart(numberForOrder, request);

        return "redirect:/products";
    }

    @PostMapping(value = "/order/create")
    public String createOrder(HttpSession httpSession) {

        orderService.createOrder(httpSession);

        return "redirect:/products";
    }

}
