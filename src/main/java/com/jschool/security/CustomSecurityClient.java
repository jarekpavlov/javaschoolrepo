package com.jschool.security;

import com.jschool.domain.Client;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class CustomSecurityClient extends Client implements UserDetails {

    public CustomSecurityClient() {}

    public CustomSecurityClient(Client client) {
        this.setAuthorities(client.getAuthorities());
        this.setAddress(client.getAddress());
        this.setDateOfBirth(client.getDateOfBirth());
        this.setEmail(client.getEmail());
        this.setId(client.getId());
        this.setName(client.getName());
        this.setOrders(client.getOrders());
        this.setPassword(client.getPassword());
        this.setPhone(client.getPhone());
        this.setSurname(client.getSurname());
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public Set<Authority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
