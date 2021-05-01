package com.jschool.domain;

import com.jschool.security.Authority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String dateOfBirth;
    private Address address;
    private String password;
    private Set<Order> orders = new HashSet<>();
    private Set<Authority> authorities;

    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "client")
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @OneToOne
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surename) {
        this.surname = surename;
    }

    @Column(length = 45)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Column(length = 45)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    @Column(length = 45)
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateofbirth) {
        this.dateOfBirth = dateofbirth;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "client") //mapped by is used to bind this SET with client field in other POJO
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    @Column(length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
