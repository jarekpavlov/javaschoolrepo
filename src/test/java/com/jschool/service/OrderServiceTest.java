package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.domain.OrderStatus;
import com.jschool.domain.PaymentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.HashSet;

class OrderServiceTest {

    @Test
    void getOrderDTO() {
        OrderService orderService = new OrderService();
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

        Assertions.assertEquals(orderDTO,orderService.getOrderDTO(order));
    }

    @Test
    void createOrder() {
    }
}