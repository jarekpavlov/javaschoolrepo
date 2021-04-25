package com.jschool.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address {
    private Long id;
    private String country;
    private String city;
    private int postCode;
    private String street;
    private short house;
    private short flat;
    private Client client;

    @OneToOne(mappedBy = "address")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @Column(length = 45)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }
    @Column(length = 45)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public short getHouse() {
        return house;
    }

    public void setHouse(short house) {
        this.house = house;
    }

    public short getFlat() {
        return flat;
    }

    public void setFlat(short flat) {
        this.flat = flat;
    }
}
