package com.jschool.domain;

public class ProductsInOrderBuilder {

    private Long id;
    private Order order;
    private Product product;
    private int quantity;
    private Float price;

    public Long getId() {
        return id;
    }

    public ProductsInOrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public ProductsInOrderBuilder setOrder(Order order) {
        this.order = order;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public ProductsInOrderBuilder setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductsInOrderBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public ProductsInOrderBuilder setPrice(Float price) {
        this.price = price;
        return this;
    }

    public ProductsInOrder build(){
        ProductsInOrder productsInOrder= new ProductsInOrder();
        productsInOrder.setProduct(product);
        productsInOrder.setOrder(order);
        productsInOrder.setPrice(price);
        productsInOrder.setId(id);
        productsInOrder.setQuantity(quantity);
        return productsInOrder;
    }
}
