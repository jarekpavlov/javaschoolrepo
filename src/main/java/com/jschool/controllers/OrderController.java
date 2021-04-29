package com.jschool.controllers;

import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {

    private EntityService entityService;

    @Autowired
    public OrderController(EntityService entityService){
        this.entityService = entityService;
    }

    @GetMapping(value = "/orders")
    public String createOrder(ModelMap map){
        List<Order> orders = entityService.entityList(Order.class);
        map.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping(value = "/order/addtocart")
    public String addToCart(@RequestParam int numberForOrder, HttpServletRequest request){

        Client client = entityService.getEntity(Client.class,6L);
        Long id =Long.parseLong(request.getParameter("id"));

        Product product = entityService.getEntity(Product.class,id);
        ProductsInOrder productsInOrder = new ProductsInOrder();
        productsInOrder.setProduct(product);
        productsInOrder.setQuantity(numberForOrder);

        HttpSession httpSession = request.getSession();
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSetTemp");
        if (productsInOrderSet!=null){
            productsInOrderSet.add(productsInOrder);
            httpSession.setAttribute("productsInOrderSetTemp",productsInOrderSet);
        }else{
            Set<ProductsInOrder> temp = new HashSet<>();
            temp.add(productsInOrder);
            httpSession.setAttribute("productsInOrderSetTemp",temp);
        }

        return "redirect:/products";
    }

}
