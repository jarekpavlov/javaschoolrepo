package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
    public String createOrder(@RequestParam int numberForOrder, HttpServletRequest request){
        Client client = entityDaoImpl.getEntity(Client.class,1L);
        Long productId = Long.parseLong(request.getParameter("id"));
        Product product = entityDaoImpl.getEntity(Product.class,productId);
        Order order = new Order();
        order.setClient(client);
        product.getOrderSet().add(order);
        order.getProductSet().add(product);
        entityDaoImpl.saveEntity(order);

        return "redirect:/products";
    }

}
