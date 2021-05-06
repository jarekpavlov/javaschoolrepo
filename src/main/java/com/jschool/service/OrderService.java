package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private EntityService entityService;
    private ModelMapper modelMapper;

    @Autowired
    public OrderService(EntityService entityService, ModelMapper modelMapper) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
    }

    /**
     * This method is taking @param numberForOrder to create a session using
     *
     * @param request with ProductsInOrder instance to store the Products into the cart
     */
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

    /**
     * The method is taking the session instance using
     *
     * @param httpSession to a new Order object to store it
     *                    into the database
     */
    public void createOrder(HttpSession httpSession, Client client) {
        Set<ProductsInOrder> productsInOrderSetTemp = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");

        Order order = new Order();
        order.setClient(client);
        order.setProductsInOrderSet(productsInOrderSetTemp);

        for (ProductsInOrder temp : productsInOrderSetTemp) {
            temp.setOrder(order);
        }
        order.setOrderStatus("In process");
        order.setPaymentStatus("Waiting for payment");
        entityService.saveEntity(order);
        httpSession.removeAttribute("productsInOrderSet");
    }

    public void deleteFromCart(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        HttpSession session = request.getSession();
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) session.getAttribute("productsInOrderSet");
        Iterator<ProductsInOrder> itr = productsInOrderSet.iterator();
        while (itr.hasNext()) {
            if (itr.next().getProduct().getId().equals(id)) {
                itr.remove();
                break;
            }
        }
        session.setAttribute("productsInOrderSet", productsInOrderSet);
    }

    public void saveFromCart(Map<String, String> quantityMap, HttpSession session, @AuthenticationPrincipal Client client) {
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) session.getAttribute("productsInOrderSet");
        for (ProductsInOrder temp : productsInOrderSet) {
            Long ProductId = temp.getProduct().getId();
            String quantity = quantityMap.get(String.valueOf(ProductId));
            temp.setQuantity(Integer.parseInt(quantity));
        }
        session.setAttribute("productsInOrderSet", productsInOrderSet);
        createOrder(session, client);
    }

    public Set<ProductsInOrder> getProductsInOrder(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        Order order = entityService.getEntity(Order.class, id);
        Set<ProductsInOrder> productsInOrderSet = order.getProductsInOrderSet();
        return productsInOrderSet;
    }

    public OrderDTO getOrderFromJoinTable(Set<ProductsInOrder> productsInOrderSet){
        ProductsInOrder productsInOrder = new ProductsInOrder();
        for (ProductsInOrder entity : productsInOrderSet) {
            productsInOrder = entity;
            System.out.println(entity);
            break;
        }
        Order order = productsInOrder.getOrder();
        return getOrderDTO(order);
    }
    public void saveOrderStatus(String orderStatus,String paymentStatus,Long id){
        Order order = entityService.getEntity(Order.class, id);
        order.setOrderStatus(orderStatus);
        order.setPaymentStatus(paymentStatus);
        entityService.updateEntity(order);
    }

    public OrderDTO getOrderDTO(Order order) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> getOrderDtoList() {
        return (entityService.entityList(Order.class))
                .stream()
                .map(this::getOrderDTO)
                .collect(Collectors.toList());
    }
}
