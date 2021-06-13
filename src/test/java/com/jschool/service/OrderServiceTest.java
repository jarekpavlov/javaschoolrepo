package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.*;
import com.jschool.exceptions.NonValidNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.*;

class OrderServiceTest {

    OrderService orderService = new OrderService();


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

    @Test
    void getPopulatedOrderTest() {

        Set<ProductsInOrder> productsInOrderSet = new HashSet<>();
        ProductsInOrder productsInOrder = new ProductsInOrderBuilder()
                .setId(1L)
                .setPrice(55F)
                .build();
        productsInOrderSet.add(productsInOrder);
        Order mockOrder = new OrderBuilder()
                .setDeliveryMethod("Home")
                .setPayment("Cash")
                .setProductsInOrderSet(productsInOrderSet)
                .build();
        Client client = new ClientBuilder()
                .setId(1L)
                .setName("John")
                .build();
        Order order = orderService.getPopulatedOrder(client, "Cash", "Home", productsInOrderSet);
        Assertions.assertEquals(mockOrder.getPayment(), order.getPayment());
        Assertions.assertEquals(mockOrder.getDeliveryMethod(), order.getDeliveryMethod());
        Assertions.assertEquals(mockOrder.getProductsInOrderSet(), order.getProductsInOrderSet());
    }

    @Test
    void setProductQuantityTest() throws NonValidNumberException {
        Map<String, String> quantityMap = new HashMap<>();
        quantityMap.put("16", "10");
        quantityMap.put("15", "12");
        Set<ProductsInOrder> productsInOrderSet = getProductsInOrdersMock();
        orderService.setProductQuantity(quantityMap, productsInOrderSet);
        Set<Integer> quantitySet = new HashSet<>();
        for (ProductsInOrder product : productsInOrderSet) {
            quantitySet.add(product.getQuantity());
        }
        Set<Integer> quantityMockSet = new HashSet<>();
        quantityMockSet.add(10);
        quantityMockSet.add(12);
        Assertions.assertEquals(quantitySet, quantityMockSet);

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
        Assertions.assertEquals(orderService.getTotalPerOrder(productsInOrderSet),100D);
    }

}