package com.jschool.DTO;

import com.jschool.domain.Address;

public class ClientDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;
    private String dateOfBirth;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean equals(Object object){
        if (this==object)
            return true;
        if(getClass()!=object.getClass())
            return false;
        if(object==null)
            return false;
        ClientDTO clientDTO = (ClientDTO) object;
        if (((ClientDTO) object).getId() == null)
            if (this.id!=null)
                return false;
        if(!this.id.equals(clientDTO.getId()))
            return false;
        return true;
    }
}
