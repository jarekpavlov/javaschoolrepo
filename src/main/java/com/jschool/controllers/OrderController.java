package com.jschool.controllers;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.*;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.service.EntityService;
import com.jschool.service.OrderService;
import com.jschool.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Value("${orders.list.quantity}")
    private int ordersOnPage;

    Logger logger = Logger.getLogger(this.getClass());
    private EntityService entityService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public OrderController(EntityService entityService, OrderService orderService, ProductService productService) {
        this.entityService = entityService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping(value = "/orders")
    public String getOrdersByClient(@RequestParam(required = false) String status, ModelMap map, @AuthenticationPrincipal Client client, HttpSession httpSession) {
        List<OrderDTO> orders = orderService.getOrderDtoList(0, Integer.MAX_VALUE);
        List<OrderDTO> ordersByClient = orders.stream()
                .filter(order -> order.getClient().equals(client)).collect(Collectors.toList());
        map.addAttribute("orders", ordersByClient);
        map.addAttribute("productsInCart", productService.getProductInCartQuantity(httpSession));
        if (status != null) {
            map.addAttribute("orderIsChanged", "order is created successfully");
        }
        return "orders";
    }

    @GetMapping(value = "/admin/orders/delete")
    public String deleteOrder(HttpServletRequest request) {
        logger.info("Employee entered the deleteMethod");
        Long id = Long.parseLong(request.getParameter("id"));
        entityService.deleteEntity(Order.class, id);
        logger.info("Employee left the deleteMethod");
        return "redirect:/admin/orders";
    }

    @GetMapping(value = "/order/add-to-cart")
    @ResponseBody
    public int addToCart(HttpSession session, @RequestParam(required = false) Long productId) {
        int numberForOrder = 1;
        orderService.addToCart(numberForOrder, session, productId);
        return productService.getProductInCartQuantity(session);
    }

    @GetMapping(value = "/order/products-in-cart")
    public String getProductsInCart(ModelMap map, HttpSession session) {
        boolean cartIsEmpty = false;
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) session.getAttribute("productsInOrderSet");
        if (productsInOrderSet == null || productsInOrderSet.size() == 0) {
            cartIsEmpty = true;
        }
        map.addAttribute("productsInCart", productsInOrderSet);
        map.addAttribute("cartIsEmpty", cartIsEmpty);
        return "cart";
    }

    @GetMapping(value = "/order/delete-from-cart")
    public String deleteProductFromCart(HttpSession session, @RequestParam(required = false) Long id) {
        orderService.deleteFromCart(session, id);
        return "redirect:/order/products-in-cart";
    }

    @PostMapping(value = "/order/save-from-cart")
    public String saveOrderFromCart(@RequestParam Map<String, String> quantityMap, HttpSession session,
                                    @AuthenticationPrincipal Client client, @RequestParam(required = false) String paymentMethod,
                                    @RequestParam(required = false) String deliveryMethod) throws NonValidNumberException {
        orderService.saveFromCart(quantityMap, session, client, paymentMethod, deliveryMethod);
        return "redirect:/orders?status=true";
    }

    @GetMapping(value = "/order/products-in-order")
    public String getProductsInOrder(HttpSession httpSession, @RequestParam Long id, ModelMap map) {
        Set<ProductsInOrder> productsInOrderSet = orderService.getProductsInOrder(id);
        OrderDTO orderDTO = orderService.getOrderFromJoinTable(productsInOrderSet);
        map.addAttribute("order", orderDTO);
        map.addAttribute("products", productsInOrderSet);
        map.addAttribute("total", orderService.getTotalPerOrder(productsInOrderSet));
        map.addAttribute("productsInCart", productService.getProductInCartQuantity(httpSession));
        return "productsPerOrder";
    }

    @PostMapping(value = "/order/repeat-order")
    public RedirectView repeatOrder(HttpSession session, @RequestParam Long orderId, RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        orderService.repeatOrder(session, orderId);
        attributes.addAttribute("id", orderId);
        return new RedirectView("/MmsPr/order/products-in-order");
    }

    @PostMapping(value = "admin/orders/save")
    public String saveOrderStatus(@RequestParam(required = false) OrderStatus orderStatus
            , @RequestParam(required = false) PaymentStatus paymentStatus
            , @RequestParam Long id) {
        orderService.saveOrderStatus(orderStatus, paymentStatus, id);
        return "redirect:/admin/orders?status=true";
    }

    @GetMapping(value = "/admin/orders")
    public String getAllOrders(ModelMap map, @RequestParam(required = false) Integer page, @RequestParam(required = false) String status) {
        map.addAttribute("orders", orderService.getPaginatedOrderList(page));
        map.addAttribute("pageQuantity", productService.getPageQuantity(orderService.getOrderDtoList(0, Integer.MAX_VALUE), ordersOnPage));
        if (status != null) {
            map.addAttribute("orderIsChanged", "order was changed successfully");
        }
        return "orders";
    }
}
