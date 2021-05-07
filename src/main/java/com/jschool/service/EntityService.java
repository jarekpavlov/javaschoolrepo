package com.jschool.service;

import com.jschool.DAO.EntityDao;
import com.jschool.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EntityService {

    private EntityDao entityDaoImpl;

    @Autowired
    public EntityService(EntityDao entityDaoImpl) {
        this.entityDaoImpl = entityDaoImpl;
    }

    public <T> T saveEntity(T entity) {
        entityDaoImpl.openCurrentSession();
        entityDaoImpl.saveEntity(entity);
        return entity;
    }

    public <T> T updateEntity(T entity) {
        entityDaoImpl.openCurrentSession();
        entityDaoImpl.updateEntity(entity);
        return entity;
    }

    public <T> T getEntity(Class<T> type, Long id) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.getEntity(type, id);
    }

    public <T> T deleteEntity(Class<T> type, Long id) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.deleteEntity(type, id);
    }

    public <T> List<T> entityList(Class<T> type) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.entityList(type);
    }

    public <T> T getEntityByEmail(Class<T> type, String email) {
        T someEntity;
        entityDaoImpl.openCurrentSession();
        List<T> entities = entityDaoImpl.getEntityByEmail(type, email);
        for (T entity : entities) {
            someEntity = entity;
            return someEntity;
        }
        return null;
    }
    public List<Order> getPeriodOrders(int daysAgo){
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.getPeriodOrders(daysAgo);
    }

}
