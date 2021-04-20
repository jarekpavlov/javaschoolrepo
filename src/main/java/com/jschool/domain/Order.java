package com.jschool.domain;


import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    private Long id;
    private Client client;
//    private String clientAddress;
    private String payment;//enum
//    private String deliveryMethod;//enum
//    // Products
//    private String paymentStatus;//enum
//
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
//
    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)//Specifying the name of Join column (is not necessary - creates automatically )
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
//
//    public String getClientAddress() {
//        return clientAddress;
//    }
//
//    public void setClientAddress(String clientAddress) {
//        this.clientAddress = clientAddress;
//    }
//
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
//
//    public String getDeliveryMethod() {
//        return deliveryMethod;
//    }
//
//    public void setDeliveryMethod(String deliveryMethod) {
//        this.deliveryMethod = deliveryMethod;
//    }
//
//    public String getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public void setPaymentStatus(String paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }
//
//    public String getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(String orderStatus) {
//        this.orderStatus = orderStatus;
//    }
//
//    private  String orderStatus;//enum


}
