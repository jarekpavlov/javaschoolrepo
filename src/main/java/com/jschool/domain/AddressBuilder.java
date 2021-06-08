package com.jschool.domain;

public class AddressBuilder {
    private Long id;
    private String country;
    private String city;
    private Integer postCode;
    private String street;
    private String house;
    private Short flat;
    private Client client;

    public Long getId() {
        return id;
    }

    public AddressBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public AddressBuilder setPostCode(Integer postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHouse() {
        return house;
    }

    public AddressBuilder setHouse(String house) {
        this.house = house;
        return this;
    }

    public Short getFlat() {
        return flat;
    }

    public AddressBuilder setFlat(Short flat) {
        this.flat = flat;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public AddressBuilder setClient(Client client) {
        this.client = client;
        return this;
    }
    public Address build(){
        Address address = new Address();
        address.setCity(city);
        address.setPostCode(postCode);
        address.setClient(client);
        address.setCountry(country);
        address.setId(id);
        address.setFlat(flat);
        address.setHouse(house);
        address.setStreet(street);
        return address;
    }
}
