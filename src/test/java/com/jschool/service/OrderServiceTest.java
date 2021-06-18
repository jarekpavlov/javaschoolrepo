package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    HttpSession httpSession;

    @Mock
    EntityService entityService;

    @InjectMocks
    OrderService orderServiceInject;

    OrderService orderService = new OrderService();
    Set<ProductsInOrder> productsInOrderSet = new HashSet<>();
    Product product;
    Product product1;

    @BeforeEach
    void init() {
        product = new ProductBuilder()
                .setId(2L)
                .setPrice(100F)
                .setBrand("brand")
                .setColor("Color")
                .build();
        product1 = new ProductBuilder()
                .setId(1L)
                .setPrice(13F)
                .setBrand("brand1")
                .setColor("Color1")
                .build();
        ProductsInOrder productsInOrder = new ProductsInOrderBuilder()
                .setId(1L)
                .setProduct(product1)
                .setPrice(100F)
                .setQuantity(1)
                .build();
        productsInOrderSet.add(productsInOrder);
    }

    @Test
    void addToCartTest() {

        when(httpSession.getAttribute("productsInOrderSet")).thenReturn(productsInOrderSet);
        when(entityService.getEntity(Product.class, 1L)).thenReturn(product1);
        orderServiceInject.addToCart(1, httpSession, 1L);
        verify(httpSession).setAttribute("productsInOrderSet", productsInOrderSet);

    }

    @Test
    void deleteForCartTest() {
        when(httpSession.getAttribute("productsInOrderSet")).thenReturn(productsInOrderSet);
        System.out.println(productsInOrderSet);
        orderServiceInject.deleteFromCart(httpSession, 1L);
        System.out.println(productsInOrderSet);
        verify(httpSession).setAttribute("productsInOrderSet", productsInOrderSet);
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

    @Test
    void getPaginatedOrderListTest() {
        Date date = new Date();

        List<OrderDTO> listOrdersDTO = new ArrayList<>();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(3L);
        orderDTO.setDateOfOrder(date);
        orderDTO.setOrderStatus(OrderStatus.DELIVERED);
        orderDTO.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        orderDTO.setClient(new Client());
        listOrdersDTO.add(orderDTO);

        List<Order> listOrders = new ArrayList<>();
        Order order = new OrderBuilder()
                .setId(3L)
                .setDateOfOrder(date)
                .setOrderStatus(OrderStatus.DELIVERED)
                .setProductsInOrderSet(new HashSet<>())
                .setPaymentStatus(PaymentStatus.PENDING_PAYMENT)
                .setClient(new Client())
                .setDeliveryMethod("deliveryMethod")
                .setProductsInOrderSet(new HashSet<>())
                .build();
        listOrders.add(order);

        orderServiceInject.setModelMapper(new ModelMapper());
        ReflectionTestUtils.setField(orderServiceInject, "ordersOnPage", 12);
        when(entityService.entityList(Order.class, 0, 12)).thenReturn(listOrders);
        List<OrderDTO> list = orderServiceInject.getPaginatedOrderList(null);
        Assertions.assertEquals(list, listOrdersDTO);

    }

}