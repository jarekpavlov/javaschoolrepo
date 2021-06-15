package com.jschool.domain;

import com.jschool.DTO.ProductDTO;

import java.util.List;

public class ProductsWithUser {

    private String role;
    private List<ProductDTO> products;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
