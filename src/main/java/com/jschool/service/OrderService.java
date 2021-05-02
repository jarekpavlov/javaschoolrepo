package com.jschool.service;

import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {

    private EntityService entityService;

    @Autowired
    public OrderService(EntityService entityService) {
        this.entityService = entityService;
    }

    public void addToCart(int numberForOrder, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));

        Product product = entityService.getEntity(Product.class, id);
        ProductsInOrder productsInOrder = new ProductsInOrder();
        productsInOrder.setProduct(product);
        productsInOrder.setQuantity(numberForOrder);

        HttpSession httpSession = request.getSession();
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
        if (productsInOrderSet != null) {
            productsInOrderSet.add(productsInOrder);
            httpSession.setAttribute("productsInOrderSet", productsInOrderSet);
        } else {
            Set<ProductsInOrder> temp = new HashSet<>();
            temp.add(productsInOrder);
            httpSession.setAttribute("productsInOrderSet", temp);
        }
    }

    public void createOrder(HttpSession httpSession) {
        Client client = entityService.getEntity(Client.class, 1L);
        Set<ProductsInOrder> productsInOrderSetTemp = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");

        Order order = new Order();
        order.setClient(client);
        order.setProductsInOrderSet(productsInOrderSetTemp);

        for (ProductsInOrder temp : productsInOrderSetTemp) {
            temp.setOrder(order);
        }
        entityService.saveEntity(order);
    }

}
