package com.jschool.domain;

import com.jschool.security.Authority;

import java.util.HashSet;
import java.util.Set;

public class ClientBuilder {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String dateOfBirth;
    private Address address;
    private String password;
    private Set<Order> orders = new HashSet<>();
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public ClientBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ClientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ClientBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClientBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ClientBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public ClientBuilder setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public ClientBuilder setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ClientBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public ClientBuilder setOrders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public ClientBuilder setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }
    public Client build(){
        Client client = new Client();
        client.setId(id);
        client.setAddress(address);
        client.setAuthorities(authorities);
        client.setEmail(email);
        client.setName(name);
        client.setSurname(surname);
        client.setDateOfBirth(dateOfBirth);
        client.setOrders(orders);
        client.setPassword(password);
        client.setPhone(phone);
        return client;
    }
}
