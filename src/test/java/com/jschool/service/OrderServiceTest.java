package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletRequest request;

    @Mock
    EntityService entityService;

    @InjectMocks
    OrderService orderService1;

    OrderService orderService = new OrderService();

    @Test
    void addToCartTest() {
//        Product product = new ProductBuilder()
//                .setId(2L)
//                .setPrice(100F)
//                .setBrand("brand")
//                .setColor("Color")
//                .build();
//        Product product1 = new ProductBuilder()
//                .setId(1L)
//                .setPrice(13F)
//                .setBrand("brand1")
//                .setColor("Color1")
//                .build();
//        ProductsInOrder productsInOrder = new ProductsInOrderBuilder()
//                .setId(1L)
//                .setProduct(product)
//                .setPrice(100F)
//                .setQuantity(1)
//                .build();
//
//        Set<ProductsInOrder> productsInOrderSet = new HashSet<>();
//        productsInOrderSet.add(productsInOrder);
//        when(httpSession.getAttribute("productsInOrderSet")).thenReturn(productsInOrderSet);
//        Set<ProductsInOrder> productsInOrderSet2 = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
//        when(entityService.getEntity(Product.class,1L)).thenReturn(product1);
//        orderService1.addToCart(1, httpSession, 1L);
        //verify(httpSession).setAttribute("productsInOrderSet", productsInOrder);
    }

    @Test
    void deleteOrderTest(){
        when(request.getParameter("id")).thenReturn("1");
        verify(entityService).deleteEntity(Order.class,1L);
    }


    @Test
    void getOrderDTO() {

        orderService.setModelMapper(new ModelMapper());
        Date date = new Date();

        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        order.setId(3L);
        order.setDateOfOrder(date);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setProductsInOrderSet(new HashSet<>());
        order.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        order.setClient(new Client());
        order.setDeliveryMethod("deliveryMethod");
        order.setProductsInOrderSet(new HashSet<>());

        orderDTO.setId(3L);
        orderDTO.setDateOfOrder(date);
        orderDTO.setOrderStatus(OrderStatus.DELIVERED);
        orderDTO.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        orderDTO.setClient(new Client());

        Assertions.assertEquals(orderDTO, orderService.getOrderDTO(order));
    }

    private Set<ProductsInOrder> getProductsInOrdersMock() {
        Client client = new ClientBuilder()
                .setId(1L)
                .setName("John")
                .build();
        Order order = new OrderBuilder()
                .setId(1L)
                .setClient(client)
                .build();
        Product product2 = new Product();
        product2.setId(15L);
        Product product1 = new Product();
        product1.setId(16L);
        Set<ProductsInOrder> productsInOrderSet = new HashSet<>();
        ProductsInOrder productsInOrder = new ProductsInOrderBuilder()
                .setId(1L)
                .setQuantity(1)
                .setOrder(order)
                .setPrice(55F)
                .setProduct(product2)
                .build();
        ProductsInOrder productsInOrder2 = new ProductsInOrderBuilder()
                .setId(2L)
                .setOrder(order)
                .setQuantity(1)
                .setProduct(product1)
                .setPrice(45F)
                .build();
        productsInOrderSet.add(productsInOrder);
        productsInOrderSet.add(productsInOrder2);
        return productsInOrderSet;
    }

    @Test
    void getOrderFromJoinTableTest() {
        orderService.setModelMapper(new ModelMapper());
        Set<ProductsInOrder> productsInOrderSet = getProductsInOrdersMock();
        OrderDTO orderDTO = orderService.getOrderFromJoinTable(productsInOrderSet);
        Assertions.assertEquals(orderDTO.getClient().getId(), 1L);
        Assertions.assertEquals(orderDTO.getClient().getName(), "John");
        Assertions.assertEquals(orderDTO.getId(), 1L);
    }

    @Test
    void getTotalPerOrderTest() {
        Set<ProductsInOrder> productsInOrderSet = getProductsInOrdersMock();
        Assertions.assertEquals(orderService.getTotalPerOrder(productsInOrderSet), 100D);
    }

}