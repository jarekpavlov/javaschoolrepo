package com.jschool.domain;

import java.util.HashSet;
import java.util.Set;

public class ProductBuilder {

    private Long id;
    private String title;
    private Float price;
    private String category;
    private String brand;
    private String color;
    private Float mass;
    private Float volume;
    private Integer quantity;
    private String imgName;
    private Set<ProductsInOrder> productsInOrderSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public ProductBuilder setPrice(Float price) {
        this.price = price;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ProductBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public Float getMass() {
        return mass;
    }

    public ProductBuilder setMass(Float mass) {
        this.mass = mass;
        return this;
    }

    public Float getVolume() {
        return volume;
    }

    public ProductBuilder setVolume(Float volume) {
        this.volume = volume;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductBuilder setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getImgName() {
        return imgName;
    }

    public ProductBuilder setImgName(String imgName) {
        this.imgName = imgName;
        return this;
    }

    public Set<ProductsInOrder> getProductsInOrderSet() {
        return productsInOrderSet;
    }

    public ProductBuilder setProductsInOrderSet(Set<ProductsInOrder> productsInOrderSet) {
        this.productsInOrderSet = productsInOrderSet;
        return this;
    }
    public Product build(){
        Product product = new Product();
        product.setId(id);
        product.setQuantity(quantity);
        product.setVolume(volume);
        product.setColor(color);
        product.setBrand(brand);
        product.setPrice(price);
        product.setCategory(category);
        product.setImgName(imgName);
        product.setTitle(title);
        product.setMass(mass);
        product.setProductsInOrderSet(productsInOrderSet);
        return product;
    }
}
