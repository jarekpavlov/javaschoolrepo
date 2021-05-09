package com.jschool.DTO;

import com.jschool.domain.Client;
import com.jschool.domain.Product;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO {
    private Long id;
    private String paymentStatus;
    private String orderStatus;
    private Client client;
    private Date dateOfOrder;
    private Set<Product> productSet = new HashSet<>();

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean equals(Object object){
        if (this==object)
            return true;
        if(getClass()!=object.getClass())
            return false;
        if(object==null)
            return false;
        OrderDTO orderDTO = (OrderDTO) object;
        if (((OrderDTO) object).getId() == null)
            if (this.id!=null)
                return false;
        if(!this.id.equals(orderDTO.getId()))
            return false;
        return true;
    }
}
