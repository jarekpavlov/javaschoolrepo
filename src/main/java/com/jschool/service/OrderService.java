package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private EntityService entityService;
    private ModelMapper modelMapper;

    @Autowired
    public OrderService(EntityService entityService, ModelMapper modelMapper) {
        this.entityService = entityService;
        this.modelMapper =modelMapper;
    }
    /**
     * This method is taking @param numberForOrder to create a session using
     * @param request  with ProductsInOrder instance to store the Products into the cart
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
     * @param httpSession to a new Order object to store it
     * into the database
     */
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
    public OrderDTO getOrderDTO(Order order){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        OrderDTO orderDTO=modelMapper.map(order,OrderDTO.class);
        return orderDTO;
    }
    public List<OrderDTO> getOrderDtoList(){
        return (entityService.entityList(Order.class))
                .stream()
                .map(this::getOrderDTO)
                .collect(Collectors.toList());
    }
}
