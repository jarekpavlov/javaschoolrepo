package com.jschool.DAO;

import com.jschool.domain.Client;

import java.util.List;

public interface EntityDao<T> {
    void save(T entity);
    Client get(Long id);
    void update(T entity);
    void delete(Long id);
    List<T> clientList();
}
