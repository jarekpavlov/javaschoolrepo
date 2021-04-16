package com.jschool.DAO;

import com.jschool.domain.Client;

import java.util.List;

public interface ClientDao {
    void save(Client client);
    Client get(Long id);
    void update(Client client);
    void delete(Long id);
    List<Client> clientList();
}
