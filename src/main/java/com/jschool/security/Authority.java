package com.jschool.security;

import com.jschool.domain.Client;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.swing.event.CaretListener;

@Entity
public class Authority implements GrantedAuthority {

    private Long id;
    private String authority;
    private Client client;


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
