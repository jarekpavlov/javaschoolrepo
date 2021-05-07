package com.jschool.controllers;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.Client;
import com.jschool.domain.ProductsInOrder;
import com.jschool.service.EntityService;
import com.jschool.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String getOrdersByClient(ModelMap map, @AuthenticationPrincipal Client client) {
        List<OrderDTO> orders = orderService.getOrderDtoList();
        List<OrderDTO> ordersByClient = orders.stream()
                .filter(order -> order.getClient().equals(client)).collect(Collectors.toList());
        map.addAttribute("orders", ordersByClient);
        return "orders";
    }

    @PostMapping(value = "/order/add-to-cart")
    public String addToCart(@RequestParam int numberForOrder, HttpServletRequest request) {
        orderService.addToCart(numberForOrder, request);
        return "redirect:/products";
    }

    @PostMapping(value = "/order/create")
    public String createOrder(HttpSession httpSession, @AuthenticationPrincipal Client client) {
        orderService.createOrder(httpSession, client);
        return "redirect:/orders";
    }

    @GetMapping(value = "/order/products-in-cart")
    public String getProductsInCart(ModelMap map, HttpSession session) {
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) session.getAttribute("productsInOrderSet");
        map.addAttribute("productsInCart", productsInOrderSet);
        return "cart";
    }

    @GetMapping(value = "/order/delete-from-cart")
    public String deleteProductFromCart(HttpServletRequest request) {
        orderService.deleteFromCart(request);
        return "redirect:/order/products-in-cart";
    }

    @PostMapping(value = "/order/save-from-cart")
    public String saveOrderFromCart(@RequestParam Map<String, String> quantityMap, HttpSession session, @AuthenticationPrincipal Client client) {
        orderService.saveFromCart(quantityMap, session, client);
        return "redirect:/orders";
    }

    @GetMapping(value = "/order/products-in-order")
    public String getProductsInOrder(HttpServletRequest request, ModelMap map) {
        Set<ProductsInOrder> productsInOrderSet = orderService.getProductsInOrder(request);
        OrderDTO orderDTO = orderService.getOrderFromJoinTable(productsInOrderSet);
        map.addAttribute("order", orderDTO);
        map.addAttribute("products", productsInOrderSet);
        return "productsPerOrder";
    }

    @PostMapping(value = "admin/orders/save")
    public String saveOrderStatus(@RequestParam String orderStatus
                                  ,@RequestParam String paymentStatus
                                  ,@RequestParam Long id) {
        orderService.saveOrderStatus(orderStatus, paymentStatus,id);
        return "redirect:/admin/orders";
    }

    @GetMapping(value = "/admin/orders")
    public String getAllOrders(ModelMap map) {
        List<OrderDTO> orderDtoList = orderService.getOrderDtoList();
        map.addAttribute("orders", orderDtoList);
        return "orders";
    }
}
