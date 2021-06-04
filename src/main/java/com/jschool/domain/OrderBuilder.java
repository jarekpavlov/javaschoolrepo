package com.jschool.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class OrderBuilder {

    private Long id;
    private Client client;
    private String payment;
    private String deliveryMethod;
    private Set<ProductsInOrder> productsInOrderSet = new HashSet<>();
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private Date dateOfOrder;

    public Long getId() {
        return id;
    }

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public OrderBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    public String getPayment() {
        return payment;
    }

    public OrderBuilder setPayment(String payment) {
        this.payment = payment;
        return this;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public OrderBuilder setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public Set<ProductsInOrder> getProductsInOrderSet() {
        return productsInOrderSet;
    }

    public OrderBuilder setProductsInOrderSet(Set<ProductsInOrder> productsInOrderSet) {
        this.productsInOrderSet = productsInOrderSet;
        return this;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public OrderBuilder setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderBuilder setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public OrderBuilder setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
        return this;
    }

    public Order build(){
        Order order = new Order();
        order.setDateOfOrder(dateOfOrder);
        order.setOrderStatus(orderStatus);
        order.setClient(client);
        order.setProductsInOrderSet(productsInOrderSet);
        order.setPayment(payment);
        order.setId(id);
        order.setPaymentStatus(paymentStatus);
        return order;
    }
}
