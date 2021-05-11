package com.jschool.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "products_in_order")
public class ProductsInOrder {

    private Long id;
    private Order order;
    private Product product;
    private int quantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(getClass()!=obj.getClass())
            return false;
        if (obj == null)
            return false;
        ProductsInOrder productsInOrder = (ProductsInOrder) obj;
        if (this.product.getId() == null ) {
            if (productsInOrder.product.getId() != null)
                return false;
        } else if (!this.product.getId().equals(productsInOrder.product.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = result * prime + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
